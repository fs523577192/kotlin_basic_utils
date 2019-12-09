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
package tech.firas.util

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 * @author Wu Yuping
 */
class IntegersTests {

    @Test
    fun testInt() {
        val random = Random.Default
        for (i in 1..10000) {
            val a = random.nextInt()
            val b = Integers.reverse(a)
            assertEquals(a, Integers.reverse(b))
            assertEquals(Integers.bitCount(a), Integers.bitCount(b))
            assertEquals(Integers.numberOfLeadingZeros(a), Integers.numberOfTrailingZeros(b))
        }
    }

    @Test
    fun testLong() {
        val random = Random.Default
        for (i in 1..10000) {
            val a = random.nextLong()
            val b = Integers.reverse(a)
            assertEquals(a, Integers.reverse(b))
            assertEquals(Integers.bitCount(a), Integers.bitCount(b))
            assertEquals(Integers.numberOfLeadingZeros(a), Integers.numberOfTrailingZeros(b))
        }
    }
}