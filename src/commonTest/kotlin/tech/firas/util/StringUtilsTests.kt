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