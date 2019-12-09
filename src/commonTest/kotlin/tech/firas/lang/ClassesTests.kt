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
package tech.firas.lang

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