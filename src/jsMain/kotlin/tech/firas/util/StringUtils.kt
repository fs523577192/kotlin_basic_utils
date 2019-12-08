package tech.firas.util

import tech.firas.lang.Character

/**
 *
 * @author Wu Yuping
 */
internal val internStringCache = HashMap<String, String>()

@JsName("internString")
actual fun internString(str: String): String {
    return internStringCache.getOrPut(str) { str }
}

/**
 * see https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/String/codePointAt
 */
@JsName("codePointAtInCharSequence")
actual fun codePointAtInCharSequence(index: Int, cs: CharSequence): Int {
    val str = cs.toString()
    if (index < 0) {
        throw IllegalArgumentException("The index must not be negative: $index")
    }
    if (index >= str.length) {
        throw IndexOutOfBoundsException("The index $index is greater than or equal to " +
                "the length of the CharSequence " + str.length)
    }
    val first = str.asDynamic().charCodeAt(index).unsafeCast<Int>()
    if (first >= tech.firas.lang.Character.MIN_HIGH_SURROGATE.toInt() &&
            first <= tech.firas.lang.Character.MAX_HIGH_SURROGATE.toInt() &&
            str.length > index + 1) {
        val second = str.asDynamic().charCodeAt(index + 1).unsafeCast<Int>()
        if (second >= tech.firas.lang.Character.MIN_LOW_SURROGATE.toInt() &&
                second <= tech.firas.lang.Character.MAX_LOW_SURROGATE.toInt()) {
            return (first - tech.firas.lang.Character.MIN_HIGH_SURROGATE.toInt()).shr(10) +
                    second - tech.firas.lang.Character.MIN_LOW_SURROGATE.toInt() + tech.firas.lang.Character.MIN_SUPPLEMENTARY_CODE_POINT
        }
    }
    return first
}