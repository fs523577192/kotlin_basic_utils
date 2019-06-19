package org.firas.util

/**
 *
 * @author Wu Yuping
 */
actual fun internString(str: String): String {
    return str.intern()
}
