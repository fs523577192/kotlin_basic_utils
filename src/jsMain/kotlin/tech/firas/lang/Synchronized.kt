package tech.firas.lang

/**
 *
 * @author Wu Yuping
 */
actual inline fun <R> synchronized(lock: Any, block: () -> R): R {
    return block()
}
