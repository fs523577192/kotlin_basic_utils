package org.firas.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

/**
 *
 * @author Wu Yuping
 */
class StringUtilsTests {
    @Test
    fun testInternString() {
        assertSame("en", internString("en"))
        assertSame("en", internString(StringBuilder().append("en").toString()))

        assertSame("cn", internString("cn"))
        assertSame("cn", internString(StringBuilder().append("cn").toString()))
    }

    @Test
    fun testCodePointAt() {
        assertEquals(66, codePointAtInCharSequence(1, "ABC"))
        assertEquals(65536, codePointAtInCharSequence(0, "\uD800\uDC00"))
    }
}