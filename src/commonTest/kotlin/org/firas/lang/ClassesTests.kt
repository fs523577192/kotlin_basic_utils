package org.firas.lang

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ClassesTests {

    @Test
    fun testGetName() {
        assertNotNull(Any::class.getName())
        assertNotNull(Number::class.getName())
        assertNotNull(Byte::class.getName())
        assertNotNull(Int::class.getName())
        assertNotNull(Long::class.getName())

        assertNotNull(CharSequence::class.getName())
        assertNotNull(String::class.getName())
        assertNotNull(StringBuilder::class.getName())

        assertNotNull(Throwable::class.getName())
        assertNotNull(Exception::class.getName())
        assertNotNull(RuntimeException::class.getName())
        assertNotNull(IllegalArgumentException::class.getName())

        assertNotNull(Map::class.getName())
        assertNotNull(MutableMap::class.getName())
        assertNotNull(HashMap::class.getName())
        assertNotNull(LinkedHashMap::class.getName())
    }

    @Test
    fun testIsAssignableFrom() {
        assertTrue(Any::class.isAssignableFrom(Number::class))
        assertTrue(Any::class.isAssignableFrom(CharSequence::class))
        assertTrue(Any::class.isAssignableFrom(String::class))
        assertTrue(Any::class.isAssignableFrom(StringBuilder::class))
        assertTrue(Any::class.isAssignableFrom(Throwable::class))
        assertTrue(Any::class.isAssignableFrom(Exception::class))
        assertTrue(Any::class.isAssignableFrom(RuntimeException::class))
        assertTrue(Any::class.isAssignableFrom(IllegalArgumentException::class))
        assertTrue(Any::class.isAssignableFrom(Map::class))
        assertTrue(Any::class.isAssignableFrom(MutableMap::class))
        assertTrue(Any::class.isAssignableFrom(HashMap::class))
        assertTrue(Any::class.isAssignableFrom(LinkedHashMap::class))

        assertTrue(CharSequence::class.isAssignableFrom(String::class))
        assertTrue(CharSequence::class.isAssignableFrom(StringBuilder::class))
        assertTrue(Appendable::class.isAssignableFrom(StringBuilder::class))

        assertTrue(Throwable::class.isAssignableFrom(Exception::class))
        assertTrue(Throwable::class.isAssignableFrom(RuntimeException::class))
        assertTrue(Throwable::class.isAssignableFrom(IllegalArgumentException::class))
        assertTrue(Exception::class.isAssignableFrom(RuntimeException::class))
        assertTrue(Exception::class.isAssignableFrom(IllegalArgumentException::class))
        assertTrue(RuntimeException::class.isAssignableFrom(IllegalArgumentException::class))

        assertTrue(Map::class.isAssignableFrom(MutableMap::class))
        assertTrue(Map::class.isAssignableFrom(HashMap::class))
        assertTrue(Map::class.isAssignableFrom(LinkedHashMap::class))
        assertTrue(MutableMap::class.isAssignableFrom(HashMap::class))
        assertTrue(MutableMap::class.isAssignableFrom(LinkedHashMap::class))
        assertTrue(HashMap::class.isAssignableFrom(LinkedHashMap::class))
    }
}