/*
 * Copyright 2002-2019 the original author or authors.
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
package org.firas.util

import org.firas.lang.Character
import kotlin.js.JsName
import kotlin.jvm.JvmStatic

/**
 * Miscellaneous {@link String} utility methods.
 *
 * <p>Mainly for internal use within the framework; consider
 * <a href="https://commons.apache.org/proper/commons-lang/">Apache's Commons Lang</a>
 * for a more comprehensive suite of {@code String} utilities.
 *
 * <p>This class delivers some simple functionality that should really be
 * provided by the core Java {@link String} and {@link StringBuilder}
 * classes. It also provides easy-to-use methods to convert between
 * delimited strings, such as CSV strings, and collections and arrays.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Keith Donald
 * @author Rob Harrop
 * @author Rick Evans
 * @author Arjen Poutsma
 * @author Sam Brannen
 * @author Brian Clozel
 * @author Wu Yuping (migrate from Spring Framework)
 */
abstract class StringUtils {

    companion object {

        /**
         * Check whether the given object (possibly a `String`) is empty.
         * This is effectly a shortcut for `!hasLength(String)`.
         *
         * This method accepts any Object as an argument, comparing it to
         * `null` and the empty String. As a consequence, this method
         * will never return `true` for a non-null non-String object.
         *
         * The Object signature is useful for general attribute handling code
         * that commonly deals with Strings but generally has to iterate over
         * Objects since attributes may e.g. be primitive value objects as well.
         *
         * **Note: If the object is typed to `String` upfront, prefer
         * [.hasLength] or [.hasText] instead.**
         * @param str the candidate object (possibly a `String`)
         * @since Spring Framework 3.2.1
         * @see .hasLength
         * @see .hasText
         */
        @JsName("isEmpty")
        @JvmStatic
        fun isEmpty(str: Any?): Boolean {
            return str == null || "" == str
        }

        /**
         * Check whether the given `CharSequence` contains actual *text*.
         *
         * More specifically, this method returns `true` if the
         * `CharSequence` is not `null`, its length is greater than
         * 0, and it contains at least one non-whitespace character.
         *
         * ```
         * StringUtils.hasText(null) = false
         * StringUtils.hasText("") = false
         * StringUtils.hasText(" ") = false
         * StringUtils.hasText("12345") = true
         * StringUtils.hasText(" 12345 ") = true
         * ```
         *
         * @param str the `CharSequence` to check (may be `null`)
         * @return `true` if the `CharSequence` is not `null`,
         * its length is greater than 0, and it does not contain whitespace only
         * @see .hasText
         * @see .hasLength
         * @see Character.isWhitespace
         */
        @JsName("hasText")
        @JvmStatic
        fun hasText(str: CharSequence?): Boolean {
            return !str.isNullOrEmpty() && containsText(str)
        }

        /**
         * Check whether the given `CharSequence` contains any whitespace characters.
         * @param str the `CharSequence` to check (may be `null`)
         * @return `true` if the `CharSequence` is not empty and
         * contains at least 1 whitespace character
         * @see Character.isWhitespace
         */
        @JsName("containsWhitespace")
        @JvmStatic
        fun containsWhitespace(str: CharSequence?): Boolean {
            if (str.isNullOrEmpty()) {
                return false
            }

            val strLen = str.length
            for (i in 0 until strLen) {
                if (Character.isWhitespace(str[i])) {
                    return true
                }
            }
            return false
        }

        /**
         * Trim leading and trailing whitespace from the given `String`.
         * @param str the `String` to check
         * @return the trimmed `String`
         * @see Character.isWhitespace
         */
        @JsName("trimWhitespace")
        @JvmStatic
        fun trimWhitespace(str: String): String {
            if (str.isEmpty()) {
                return str
            }

            var beginIndex = 0
            var endIndex = str.length - 1

            while (beginIndex <= endIndex && Character.isWhitespace(str[beginIndex])) {
                beginIndex += 1
            }

            while (endIndex > beginIndex && Character.isWhitespace(str[endIndex])) {
                endIndex -= 1
            }

            return str.substring(beginIndex, endIndex + 1)
        }

        /**
         * Trim *all* whitespace from the given `String`:
         * leading, trailing, and in between characters.
         * @param str the `String` to check
         * @return the trimmed `String`
         * @see Character.isWhitespace
         */
        @JsName("trimAllWhitespace")
        @JvmStatic
        fun trimAllWhitespace(str: String): String {
            if (str.isEmpty()) {
                return str
            }

            val len = str.length
            val sb = StringBuilder(str.length)
            for (i in 0 until len) {
                val c = str[i]
                if (!Character.isWhitespace(c)) {
                    sb.append(c)
                }
            }
            return sb.toString()
        }

        /**
         * Test if the given `String` starts with the specified prefix,
         * ignoring upper/lower case.
         * @param str the `String` to check
         * @param prefix the prefix to look for
         * @see String.startsWith
         */
        @JsName("startsWithIgnoreCase")
        @JvmStatic
        fun startsWithIgnoreCase(str: String?, prefix: String?): Boolean {
            return str != null && prefix != null && str.length >= prefix.length &&
                    str.regionMatches(0, prefix, 0, prefix.length, ignoreCase = true)
        }

        /**
         * Test if the given `String` ends with the specified suffix,
         * ignoring upper/lower case.
         * @param str the `String` to check
         * @param suffix the suffix to look for
         * @see String.endsWith
         */
        @JsName("endsWithIgnoreCase")
        @JvmStatic
        fun endsWithIgnoreCase(str: String?, suffix: String?): Boolean {
            return str != null && suffix != null && str.length >= suffix.length &&
                    str.regionMatches(str.length - suffix.length, suffix, 0, suffix.length, ignoreCase = true)
        }

        /**
         * Test whether the given string matches the given substring
         * at the given index.
         * @param str the original string (or StringBuilder)
         * @param index the index in the original string to start matching against
         * @param substring the substring to match at the given index
         */
        @JsName("substringMatch")
        @JvmStatic
        fun substringMatch(str: CharSequence, index: Int, substring: CharSequence): Boolean {
            if (index + substring.length > str.length) {
                return false
            }
            for (i in 0 until substring.length) {
                if (str[index + i] != substring[i]) {
                    return false
                }
            }
            return true
        }

        /**
         * Count the occurrences of the substring `sub` in string `str`.
         * @param str string to search in
         * @param sub string to search for
         */
        @JsName("countOccurrencesOf")
        @JvmStatic
        fun countOccurrencesOf(str: String, sub: String): Int {
            if (str.isEmpty() || sub.isEmpty()) {
                return 0
            }

            var count = 0
            var pos = 0
            while (true) {
                val idx = str.indexOf(sub, pos)
                if (idx == -1) {
                    break
                }
                count += 1
                pos = idx + sub.length
            }
            return count
        }

        /**
         * Replace all occurrences of a substring within a string with another string.
         * @param inString `String` to examine
         * @param oldPattern `String` to replace
         * @param newPattern `String` to insert
         * @return a `String` with the replacements
         */
        @JsName("replace")
        @JvmStatic
        fun replace(inString: String, oldPattern: String, newPattern: String?): String {
            if (inString.isEmpty() || oldPattern.isEmpty() || newPattern == null) {
                return inString
            }
            var index = inString.indexOf(oldPattern)
            if (index == -1) {
                // no occurrence -> can return input as-is
                return inString
            }

            var capacity = inString.length
            if (newPattern.length > oldPattern.length) {
                capacity += 16
            }
            val sb = StringBuilder(capacity)

            var pos = 0  // our position in the old string
            val patLen = oldPattern.length
            while (index >= 0) {
                sb.append(inString.substring(pos, index))
                sb.append(newPattern)
                pos = index + patLen
                index = inString.indexOf(oldPattern, pos)
            }

            // append any characters to the right of a match
            sb.append(inString.substring(pos))
            return sb.toString()
        }

        private const val FOLDER_SEPARATOR = "/"

        private const val WINDOWS_FOLDER_SEPARATOR = "\\"

        private const val TOP_PATH = ".."

        private const val CURRENT_PATH = "."

        private const val EXTENSION_SEPARATOR = '.'

        @JvmStatic
        private fun containsText(str: CharSequence): Boolean {
            val strLen = str.length
            for (i in 0 until strLen) {
                if (!Character.isWhitespace(str[i])) {
                    return true
                }
            }
            return false
        }
    }
}

expect fun internString(str: String): String
expect fun codePointAtInCharSequence(index: Int, cs: CharSequence): Int
