package org.firas.util.concurrent

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 *
 * @author Wu Yuping
 */
class ConcurrentHashMapTests {

    @Test
    fun test() {
        var map: MutableMap<Int, String> = getConcurrentHashMap(Int::class, String::class)
        assertTrue(map.isEmpty())
        map[1] = "11"
        assertEquals("11", map[1])

        map = getConcurrentHashMap(Int::class, String::class, 1)
        assertTrue(map.isEmpty())
        map[1] = "11"
        assertEquals("11", map[1])

        map = getConcurrentHashMap(Int::class, String::class, 1, 1f)
        assertTrue(map.isEmpty())
        map[1] = "11"
        assertEquals("11", map[1])

        map = getConcurrentHashMap(Int::class, String::class, 1, 1f, 2)
        assertTrue(map.isEmpty())
        map[1] = "11"
        assertEquals("11", map[1])
    }
}