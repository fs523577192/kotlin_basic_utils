package org.firas.promise

/**
 *
 * @author Wu Yuping
 */
expect class Promise<T> {
    constructor(task: () -> T)

    constructor(task: (resolve: (T?) -> Any?, reject: (Exception) -> Any?) -> Unit)

    fun getState(): PromiseState

    fun <OTHER> then(consumer: (T?) -> OTHER): Promise<OTHER>

    fun <OTHER> thenPromise(consumer: (T?) -> Promise<OTHER>): Promise<OTHER>

    fun <OTHER> then(consumer: (T?) -> OTHER, errorHandler: (Exception) -> Promise<*>): Promise<OTHER>

    fun <OTHER> thenPromise(consumer: (T?) -> Promise<OTHER>, errorHandler: (Exception) -> Promise<*>): Promise<OTHER>

    fun <OTHER> catch(errorHandler: (Exception) -> OTHER): Promise<OTHER>

    fun <OTHER> catchPromise(errorHandler: (Exception) -> Promise<OTHER>): Promise<OTHER>
}

expect fun promiseAll(promises: Array<Promise<*>>): Promise<Array<*>>
expect fun promiseAll(promises: List<Promise<*>>): Promise<List<*>>