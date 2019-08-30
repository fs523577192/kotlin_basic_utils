package org.firas.promise

import org.firas.lang.synchronized
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 * @author Wu Yuping
 */
actual class Promise<T> {

    @Volatile
    private var state: PromiseState = PromiseState.PENDING

    private val lockForState: Any = Any()

    private var lockForThen: Queue<ArrayBlockingQueue<Pair<Boolean, T?>>> = ConcurrentLinkedQueue()
    private var lockForCatch: Queue<ArrayBlockingQueue<Pair<Boolean, Exception?>>> = ConcurrentLinkedQueue()

    actual constructor(task: () -> T): this({ paramResolve, paramReject ->
        try {
            paramResolve(task())
        } catch (ex: Exception) {
            paramReject(ex)
        }
    })

    actual constructor(task: ((T?) -> Any?, (Exception) -> Any?) -> Unit) {
        threadPool.execute {
            try {
                task({ result -> fulfill(result) }, { ex -> reject(ex) })
            } catch (ex: Exception) {
                reject(ex)
            }
        }
    }

    @Throws(Exception::class)
    private fun fulfill(result: T?) {
        synchronized(this.lockForState) {
            state = PromiseState.FULFILLED
        }
        while (this.lockForThen.isNotEmpty()) {
            this.lockForThen.poll().offer(Pair(true, result))
        }
        while (this.lockForCatch.isNotEmpty()) {
            this.lockForCatch.poll().offer(Pair(false, null))
        }
    }

    private fun reject(ex: Exception) {
        synchronized(this.lockForState) {
            state = PromiseState.REJECTED
        }
        while (this.lockForThen.isNotEmpty()) {
            this.lockForThen.poll().offer(Pair(false, null))
        }
        while (this.lockForCatch.isNotEmpty()) {
            this.lockForCatch.poll().offer(Pair(true, ex))
        }
    }

    actual fun getState(): PromiseState {
        return this.state
    }

    actual fun <OTHER> then(consumer: (T?) -> OTHER): Promise<OTHER> {
        return synchronized(lockForState) {
            checkPending()

            val forSignal = ArrayBlockingQueue<Pair<Boolean, T?>>(1)
            lockForThen.offer(forSignal)

            Promise{ paramResolve, paramReject ->
                Thread {
                    val pair = forSignal.poll()
                    if (pair.first) {
                        threadPool.execute {
                            try {
                                paramResolve(consumer(pair.second))
                            } catch (ex: Exception) {
                                paramReject(ex)
                            }
                        }
                    } // if (the promise on whom thenPromise is called) is resolved
                }.start()
            }
        }
    }

    actual fun <OTHER> thenPromise(consumer: (T?) -> Promise<OTHER>): Promise<OTHER> {
        return synchronized(lockForState) {
            checkPending()

            val forSignal = ArrayBlockingQueue<Pair<Boolean, T?>>(1)
            lockForThen.offer(forSignal)

            Promise{ paramResolve, paramReject ->
                Thread {
                    val pair = forSignal.poll()
                    if (pair.first) {
                        threadPool.execute {
                            try {
                                consumer(pair.second).then{ result ->
                                    paramResolve(result)
                                    result
                                }
                            } catch (ex: Exception) {
                                paramReject(ex)
                            }
                        }
                    } // if (the promise on whom thenPromise is called) is resolved
                }.start()
            }
        }
    }

    actual fun <OTHER> then(consumer: (T?) -> OTHER, errorHandler: (Exception) -> Promise<*>): Promise<OTHER> {
        return synchronized(this.lockForState) {
            this.catch(errorHandler)
            this.then(consumer)
        }
    }

    actual fun <OTHER> thenPromise(consumer: (T?) -> Promise<OTHER>, errorHandler: (Exception) -> Promise<*>): Promise<OTHER> {
        return synchronized(this.lockForState) {
            this.catch(errorHandler)
            this.thenPromise(consumer)
        }
    }

    @Throws(IllegalPromiseStateException::class)
    private fun checkPending() {
        if (PromiseState.FULFILLED == this.state) {
            throw IllegalPromiseStateException("The promise has been fulfilled")
        } else if (PromiseState.REJECTED == this.state) {
            throw IllegalPromiseStateException("The promise has been rejected")
        }
    }

    actual fun <OTHER> catch(errorHandler: (Exception) -> OTHER): Promise<OTHER> {
        return synchronized(lockForState) {
            checkPending()

            val forSignal = ArrayBlockingQueue<Pair<Boolean, Exception?>>(1)
            lockForCatch.offer(forSignal)

            Promise{ paramResolve, paramReject ->
                Thread {
                    val pair = forSignal.poll()
                    if (pair.first) {
                        threadPool.execute {
                            try {
                                paramResolve(errorHandler(pair.second!!))
                            } catch (ex: Exception) {
                                paramReject(ex)
                            }
                        }
                    } // if (the promise on whom thenPromise is called) is rejected
                }.start()
            }
        }
    }

    actual fun <OTHER> catchPromise(errorHandler: (Exception) -> Promise<OTHER>): Promise<OTHER> {
        return synchronized(lockForState) {
            checkPending()

            val forSignal = ArrayBlockingQueue<Pair<Boolean, Exception?>>(1)
            lockForCatch.offer(forSignal)

            Promise{ paramResolve, paramReject ->
                Thread {
                    val pair = forSignal.poll()
                    if (pair.first) {
                        threadPool.execute {
                            try {
                                errorHandler(pair.second!!).then{ result ->
                                    paramResolve(result)
                                    result
                                }
                            } catch (ex: Exception) {
                                paramReject(ex)
                            }
                        }
                    } // if (the promise on whom thenPromise is called) is rejected
                }.start()
            }
        }
    }

    companion object {

        private val threadPool = ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(), 0, TimeUnit.SECONDS,
                LinkedBlockingQueue())

        @JvmStatic
        fun all(promises: Array<Promise<*>>): Promise<Array<*>> {
            val lockForPromise = LockForPromiseAll<Any?>(promises.size)
            for (i in 0 until promises.size) {
                promises[i].then {
                    lockForPromise.provide(i, it)
                }
            }
            return Promise{ paramResolve, paramReject ->
                Thread {
                    try {
                        paramResolve(lockForPromise.consume().toTypedArray())
                    } catch (ex: Exception) {
                        paramReject(ex)
                    }
                }.start()
            }
        }

        @JvmStatic
        fun all(promises: List<Promise<*>>): Promise<List<*>> {
            val lockForPromise = LockForPromiseAll<Any?>(promises.size)
            var i = 0
            for (promise in promises) {
                val j = i
                i += 1
                promise.then { lockForPromise.provide(j, it) }
            }
            return Promise{ paramResolve, paramReject ->
                Thread {
                    try {
                        paramResolve(lockForPromise.consume())
                    } catch (ex: Exception) {
                        paramReject(ex)
                    }
                }.start()
            }
        }

        private class LockForPromiseAll<E>(num: Int) {
            private val atomicInt = AtomicInteger(num)
            private val forSignal = ArrayBlockingQueue<Boolean>(1)
            private val result = ArrayList<Any?>(num)

            internal fun consume(): List<E> {
                while (this.forSignal.poll()) {
                    if (this.atomicInt.decrementAndGet() <= 0) {
                        break
                    }
                }
                return this.result as List<E>
            }

            internal fun provide(index: Int, item: E) {
                this.result[index] = item
                this.forSignal.offer(true)
            }
        } // private class LockForPromiseAll
    }
}

actual fun promiseAll(promises: Array<Promise<*>>): Promise<Array<*>> = Promise.all(promises)

actual fun promiseAll(promises: List<Promise<*>>): Promise<List<*>> = Promise.all(promises)