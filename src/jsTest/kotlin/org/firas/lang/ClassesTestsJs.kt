package org.firas.lang

import kotlin.test.Test
import kotlin.test.assertTrue

/**
 *
 * @author Wu Yuping
 */
class ClassesTestsJs {

    @Test
    fun testIsAssignableFrom() {
        assertTrue(Any::class.isAssignableFrom(Byte::class))
        assertTrue(Any::class.isAssignableFrom(Int::class))
        assertTrue(Any::class.isAssignableFrom(Long::class))

        assertTrue(Number::class.isAssignableFrom(Byte::class))
        assertTrue(Number::class.isAssignableFrom(Int::class))
        assertTrue(Number::class.isAssignableFrom(Long::class))
    }
}