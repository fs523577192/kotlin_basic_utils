/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.firas.util.concurrent

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