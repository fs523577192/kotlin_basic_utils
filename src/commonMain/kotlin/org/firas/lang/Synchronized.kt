package org.firas.lang

/**
 *
 * @author Wu Yuping
 */
expect inline fun <R> synchronized(lock: Any, block: () -> R): R