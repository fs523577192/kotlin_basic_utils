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