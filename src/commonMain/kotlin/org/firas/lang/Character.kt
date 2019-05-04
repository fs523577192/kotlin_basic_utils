/*
 * Migrated from the source code of OpenJDK/jdk8 by Wu Yuping
 *
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.firas.lang

/**
 *
 * @author Wu Yuping
 */
class Character {
    companion object {
        /**
         * The minimum radix available for conversion to and from strings.
         * The constant value of this field is the smallest value permitted
         * for the radix argument in radix-conversion methods such as the
         * `digit` method, the `forDigit` method, and the
         * `toString` method of class `Integer`.
         *
         * @see Character.digit
         * @see Character.forDigit
         * @see Int.toString
         * @see String.toInt
         */
        val MIN_RADIX = 2

        /**
         * The maximum radix available for conversion to and from strings.
         * The constant value of this field is the largest value permitted
         * for the radix argument in radix-conversion methods such as the
         * `digit` method, the `forDigit` method, and the
         * `toString` method of class `Integer`.
         *
         * @see Character.digit
         * @see Character.forDigit
         * @see Int.toString
         * @see String.toInt
         */
        val MAX_RADIX = 36

        /**
         * Determines if the specified character is a digit.
         * <p>
         * A character is a digit if its general category type, provided
         * by {@code Character.getType(ch)}, is
         * `DECIMAL_DIGIT_NUMBER`.
         * <p>
         * Some Unicode character ranges that contain digits:
         * <ul>
         * <li>{@code '\u005Cu0030'} through {@code '\u005Cu0039'},
         *     ISO-LATIN-1 digits ({@code '0'} through {@code '9'})
         * <li>{@code '\u005Cu0660'} through {@code '\u005Cu0669'},
         *     Arabic-Indic digits
         * <li>{@code '\u005Cu06F0'} through {@code '\u005Cu06F9'},
         *     Extended Arabic-Indic digits
         * <li>{@code '\u005Cu0966'} through {@code '\u005Cu096F'},
         *     Devanagari digits
         * <li>{@code '\u005CuFF10'} through {@code '\u005CuFF19'},
         *     Fullwidth digits
         * </ul>
         *
         * Many other character ranges contain digits as well.
         *
         * <p><b>Note:</b> This method cannot handle <a
         * href="#supplementary"> supplementary characters</a>. To support
         * all Unicode characters, including supplementary characters, use
         * the {@link #isDigit(int)} method.
         *
         * @param   ch   the character to be tested.
         * @return  `true` if the character is a digit;
         *          `false` otherwise.
         * @see     Character#digit(char, int)
         * @see     Character#forDigit(int, int)
         * @see     Character#getType(char)
         */
        fun isDigit(ch: Char): Boolean {
            return isDigit(ch.toInt())
        }

        /**
         * Determines if the specified character (Unicode code point) is a digit.
         * <p>
         * A character is a digit if its general category type, provided
         * by {@link Character#getType(int) getType(codePoint)}, is
         * `DECIMAL_DIGIT_NUMBER`.
         * <p>
         * Some Unicode character ranges that contain digits:
         * <ul>
         * <li>{@code '\u005Cu0030'} through {@code '\u005Cu0039'},
         *     ISO-LATIN-1 digits ({@code '0'} through {@code '9'})
         * <li>{@code '\u005Cu0660'} through {@code '\u005Cu0669'},
         *     Arabic-Indic digits
         * <li>{@code '\u005Cu06F0'} through {@code '\u005Cu06F9'},
         *     Extended Arabic-Indic digits
         * <li>{@code '\u005Cu0966'} through {@code '\u005Cu096F'},
         *     Devanagari digits
         * <li>{@code '\u005CuFF10'} through {@code '\u005CuFF19'},
         *     Fullwidth digits
         * </ul>
         *
         * Many other character ranges contain digits as well.
         *
         * @param   codePoint the character (Unicode code point) to be tested.
         * @return  `true` if the character is a digit;
         *          `false` otherwise.
         * @see     Character#forDigit(int, int)
         * @see     Character#getType(int)
         * @since   Java 1.5
         */
        fun isDigit(codePoint: Int): Boolean {
            return codePoint in '0'.toInt()..'9'.toInt() || codePoint in '０'.toInt()..'９'.toInt()
                    || codePoint in 0x660..0x669 || codePoint in 0x6F0..0x6F9 || codePoint in 0x966..0x96F
        }

        /**
         * Determines if the specified character is white space according to Java.
         * A character is a Java whitespace character if and only if it satisfies
         * one of the following criteria:
         *
         *  *  It is a Unicode space character (`SPACE_SEPARATOR`,
         * `LINE_SEPARATOR`, or `PARAGRAPH_SEPARATOR`)
         * but is not also a non-breaking space (`'\u005Cu00A0'`,
         * `'\u005Cu2007'`, `'\u005Cu202F'`).
         *  *  It is `'\u005Ct'`, U+0009 HORIZONTAL TABULATION.
         *  *  It is `'\u005Cn'`, U+000A LINE FEED.
         *  *  It is `'\u005Cu000B'`, U+000B VERTICAL TABULATION.
         *  *  It is `'\u005Cf'`, U+000C FORM FEED.
         *  *  It is `'\u005Cr'`, U+000D CARRIAGE RETURN.
         *  *  It is `'\u005Cu001C'`, U+001C FILE SEPARATOR.
         *  *  It is `'\u005Cu001D'`, U+001D GROUP SEPARATOR.
         *  *  It is `'\u005Cu001E'`, U+001E RECORD SEPARATOR.
         *  *  It is `'\u005Cu001F'`, U+001F UNIT SEPARATOR.
         *
         *
         *
         * **Note:** This method cannot handle [ supplementary characters](#supplementary). To support
         * all Unicode characters, including supplementary characters, use
         * the [.isWhitespace] method.
         *
         * @param   ch the character to be tested.
         * @return  `true` if the character is a Java whitespace
         * character; `false` otherwise.
         * @see Character.isSpaceChar
         * @since  OpenJDK 1.1
         */
        fun isWhitespace(ch: Char): Boolean {
            return isWhitespace(ch.toInt())
        }

        /**
         * Determines if the specified character (Unicode code point) is
         * white space according to Java.  A character is a Java
         * whitespace character if and only if it satisfies one of the
         * following criteria:
         *
         *  *  It is a Unicode space character ([.SPACE_SEPARATOR],
         * [.LINE_SEPARATOR], or [.PARAGRAPH_SEPARATOR])
         * but is not also a non-breaking space (`'\u005Cu00A0'`,
         * `'\u005Cu2007'`, `'\u005Cu202F'`).
         *  *  It is `'\u005Ct'`, U+0009 HORIZONTAL TABULATION.
         *  *  It is `'\u005Cn'`, U+000A LINE FEED.
         *  *  It is `'\u005Cu000B'`, U+000B VERTICAL TABULATION.
         *  *  It is `'\u005Cf'`, U+000C FORM FEED.
         *  *  It is `'\u005Cr'`, U+000D CARRIAGE RETURN.
         *  *  It is `'\u005Cu001C'`, U+001C FILE SEPARATOR.
         *  *  It is `'\u005Cu001D'`, U+001D GROUP SEPARATOR.
         *  *  It is `'\u005Cu001E'`, U+001E RECORD SEPARATOR.
         *  *  It is `'\u005Cu001F'`, U+001F UNIT SEPARATOR.
         *
         *
         * @param   codePoint the character (Unicode code point) to be tested.
         * @return  `true` if the character is a Java whitespace
         * character; `false` otherwise.
         * @see Character.isSpaceChar
         * @since  OpenJDK 1.5
         */
        fun isWhitespace(codePoint: Int): Boolean {
            return codePoint == ' '.toInt() || codePoint == '\t'.toInt()
                    || codePoint == '\n'.toInt() || codePoint == '\r'.toInt()
                    || codePoint == 0x0B || codePoint == 0x0C
                    || codePoint == 0x1C || codePoint == 0x1D || codePoint == 0x1E || codePoint == 0x1F
        }

        /**
         * Returns the numeric value of the character `ch` in the
         * specified radix.
         *
         *
         * If the radix is not in the range `MIN_RADIX`
         * `radix`  `MAX_RADIX` or if the
         * value of `ch` is not a valid digit in the specified
         * radix, `-1` is returned. A character is a valid digit
         * if at least one of the following is true:
         *
         *  * The method `isDigit` is `true` of the character
         * and the Unicode decimal digit value of the character (or its
         * single-character decomposition) is less than the specified radix.
         * In this case the decimal digit value is returned.
         *  * The character is one of the uppercase Latin letters
         * `'A'` through `'Z'` and its code is less than
         * `radix + 'A' - 10`.
         * In this case, `ch - 'A' + 10`
         * is returned.
         *  * The character is one of the lowercase Latin letters
         * `'a'` through `'z'` and its code is less than
         * `radix + 'a' - 10`.
         * In this case, `ch - 'a' + 10`
         * is returned.
         *  * The character is one of the fullwidth uppercase Latin letters A
         * (`'\u005CuFF21'`) through Z (`'\u005CuFF3A'`)
         * and its code is less than
         * `radix + '\u005CuFF21' - 10`.
         * In this case, `ch - '\u005CuFF21' + 10`
         * is returned.
         *  * The character is one of the fullwidth lowercase Latin letters a
         * (`'\u005CuFF41'`) through z (`'\u005CuFF5A'`)
         * and its code is less than
         * `radix + '\u005CuFF41' - 10`.
         * In this case, `ch - '\u005CuFF41' + 10`
         * is returned.
         *
         *
         *
         * **Note:** This method cannot handle [ supplementary characters](#supplementary). To support
         * all Unicode characters, including supplementary characters, use
         * the [.digit] method.
         *
         * @param   ch      the character to be converted.
         * @param   radix   the radix.
         * @return  the numeric value represented by the character in the
         * specified radix.
         * @see Character.forDigit
         * @see Character.isDigit
         * @author Wu Yuping
         */
        fun digit(ch: Char, radix: Int): Int {
            if (radix < MIN_RADIX || radix > MAX_RADIX) {
                throw IllegalArgumentException("Invalid radix: $radix")
            }
            val result = when (ch) {
                in '0' .. '9' -> {
                    ch.toInt() - '0'.toInt()
                }
                in 'A' .. 'Z' -> {
                    ch.toInt() - 'A'.toInt() + 10
                }
                in 'a' .. 'z' -> {
                    ch.toInt() - 'a'.toInt() + 10
                }
                // TODO: full-width character
                else -> throw NumberFormatException("Not a valid digit: $ch")
            }
            if (result >= radix) {
                throw NumberFormatException("Not a valid digit ($ch) with the radix $radix")
            }
            return result
        }

        /**
         * @author Wu Yuping
         */
        fun stringToCharArray(str: String): CharArray {
            return str.toList().toCharArray()
        }
    }  // companion object
}