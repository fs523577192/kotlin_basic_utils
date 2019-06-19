package org.firas.util

/**
 *
 * @author Wu Yuping
 */
internal val internStringCache = HashMap<String, String>()

@JsName("internString")
actual fun internString(str: String): String {
    return internStringCache.getOrPut(str) { str }
}