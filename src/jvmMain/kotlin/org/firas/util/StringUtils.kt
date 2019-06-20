package org.firas.util

/**
 *
 * @author Wu Yuping
 */
actual fun internString(str: String): String {
    return str.intern()
}

actual fun codePointAtInCharSequence(index: Int, cs: CharSequence): Int {
    return if (cs is String) cs.codePointAt(index)
            else if (cs is StringBuilder) cs.codePointAt(index)
            else if (cs is StringBuffer) cs.codePointAt(index)
            else cs.toString().codePointAt(index)
}
