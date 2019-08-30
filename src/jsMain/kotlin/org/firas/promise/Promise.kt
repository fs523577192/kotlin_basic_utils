package org.firas.promise

/**
 *
 * @author Wu Yuping
 */
actual class Promise<T> {

    private val nativePromise: dynamic
    private var state: PromiseState = PromiseState.PENDING

    private constructor(nativePromise: Any) {
        this.nativePromise = nativePromise.asDynamic().then({ result: T? ->
            state = PromiseState.FULFILLED
            result
        }, { ex: Exception ->
            state = PromiseState.REJECTED
            ex
        })
    }

    actual constructor(task: () -> T) {
        val executable = { resolve: (T) -> Unit, reject: (Exception) -> Unit ->
            try {
                val result = task()
                state = PromiseState.FULFILLED
                resolve(result)
            } catch (ex: Exception) {
                state = PromiseState.REJECTED
                reject(ex)
            }
        }
        this.nativePromise = js("new Promise(executable)")
    }

    actual constructor(task: (resolve: (T?) -> Any?, reject: (Exception) -> Any?) -> Unit) {
        val executable = { resolve: (T?) -> Unit, reject: (Exception) -> Unit ->
            task({ t ->
                state = PromiseState.FULFILLED
                resolve(t)
            }, { ex ->
                state = PromiseState.REJECTED
                reject(ex)
            })
        }
        this.nativePromise = js("new Promise(executable)")
    }

    actual fun getState(): PromiseState {
        return this.state
    }

    @JsName("then")
    actual fun <OTHER> then(consumer: (T?) -> OTHER): Promise<OTHER> {
        return Promise(this.nativePromise.then(consumer) as Any)
    }

    @JsName("thenPromise")
    actual fun <OTHER> thenPromise(consumer: (T?) -> Promise<OTHER>): Promise<OTHER> {
        return Promise(this.nativePromise.then(consumer) as Any)
    }

    @JsName("thenWithErrorHandler")
    actual fun <OTHER> then(consumer: (T?) -> OTHER, errorHandler: (Exception) -> Promise<*>): Promise<OTHER> {
        return Promise(this.nativePromise.then(consumer, errorHandler) as Any)
    }

    @JsName("thenPromiseWithErrorHandler")
    actual fun <OTHER> thenPromise(consumer: (T?) -> Promise<OTHER>, errorHandler: (Exception) -> Promise<*>): Promise<OTHER> {
        return Promise(this.nativePromise.then(consumer, errorHandler) as Any)
    }

    @JsName("catch")
    actual fun <OTHER> catch(errorHandler: (Exception) -> OTHER): Promise<OTHER> {
        return Promise(this.nativePromise.catch(errorHandler) as Any)
    }

    @JsName("catchPromise")
    actual fun <OTHER> catchPromise(errorHandler: (Exception) -> Promise<OTHER>): Promise<OTHER> {
        return Promise(this.nativePromise.catch(errorHandler) as Any)
    }

    // actual fun finally(() -> Any?)

    companion object {
        fun all(promises: Array<Promise<*>>): Promise<Array<*>> {
            val nativePromises = promises.map { it.nativePromise }.toTypedArray()
            return Promise(js("Promise.all(nativePromises)") as Any)
        }

        fun allInList(promises: List<Promise<*>>): Promise<List<*>> {
            val nativePromises = promises.toTypedArray()
            return Promise<Array<*>>(js("Promise.all(nativePromises)") as Any)
                    .then { it!!.asList() }
        }
    }
}

actual fun promiseAll(promises: Array<Promise<*>>): Promise<Array<*>> = Promise.all(promises)

actual fun promiseAll(promises: List<Promise<*>>): Promise<List<*>> = Promise.allInList(promises)