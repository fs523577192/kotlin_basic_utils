/*
 * Copyright (c) 1994, 2004, Oracle and/or its affiliates. All rights reserved.
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
package tech.firas.util

import tech.firas.lang.Character
import kotlin.js.JsName


/**
 * The string tokenizer class allows an application to break a
 * string into tokens. The tokenization method is much simpler than
 * the one used by the `StreamTokenizer` class. The
 * `StringTokenizer` methods do not distinguish among
 * identifiers, numbers, and quoted strings, nor do they recognize
 * and skip comments.
 *
 *
 * The set of delimiters (the characters that separate tokens) may
 * be specified either at creation time or on a per-token basis.
 *
 *
 * An instance of `StringTokenizer` behaves in one of two
 * ways, depending on whether it was created with the
 * `returnDelims` flag having the value `true`
 * or `false`:
 *
 * * If the flag is `false`, delimiter characters serve to
 *     separate tokens. A token is a maximal sequence of consecutive
 *     characters that are not delimiters.
 * * If the flag is `true`, delimiter characters are themselves
 *     considered to be tokens. A token is thus either one delimiter
 *     character, or a maximal sequence of consecutive characters that are
 *     not delimiters.
 *
 *
 * A `StringTokenizer` object internally maintains a current
 * position within the string to be tokenized. Some operations advance this
 * current position past the characters processed.<p>
 * A token is returned by taking a substring of the string that was used to
 * create the `StringTokenizer` object.
 *
 *
 * The following is one example of the use of the tokenizer. The code:
 * ```
 *     val st = StringTokenizer("this is a test")
 *     while (st.hasMoreTokens()) {
 *         println(st.nextToken())
 *     }
 * ```
 *
 *
 * prints the following output:
 * <blockquote><pre>
 *     this
 *     is
 *     a
 *     test
 * </pre></blockquote>
 *
 *
 *
 * `StringTokenizer` is a legacy class that is retained for
 * compatibility reasons although its use is discouraged in new code. It is
 * recommended that anyone seeking this functionality use the `split`
 * method of `String` or the java.util.regex package instead.
 *
 *
 * The following example illustrates how the `String.split`
 * method can be used to break up a string into its basic tokens:
 * ```
 *     val result: Array<String> = "this is a test".split("\\s")
 *     for (x in 0 until result.length)
 *         println(result[x])
 * ```
 *
 * prints the following output:
 * <blockquote><pre>
 *     this
 *     is
 *     a
 *     test
 * </pre></blockquote>
 *
 * @author  unascribed
 * @author Wu Yuping (migrate from OpenJDK 11)
 * @since   Java 1.0
 */
class StringTokenizer: Iterator<String> {

    private var currentPosition: Int
    private var newPosition: Int
    private var maxPosition: Int
    private var str: String
    private var delimiters: String? = null
    private var retDelims: Boolean
    private var delimsChanged: Boolean

    /**
     * maxDelimCodePoint stores the value of the delimiter character with the
     * highest value. It is used to optimize the detection of delimiter
     * characters.
     *
     * It is unlikely to provide any optimization benefit in the
     * hasSurrogates case because most string characters will be
     * smaller than the limit, but we keep it so that the two code
     * paths remain similar.
     */
    private var maxDelimCodePoint: Int = 0

    /**
     * If delimiters include any surrogates (including surrogate
     * pairs), hasSurrogates is true and the tokenizer uses the
     * different code path. This is because String.indexOf(int)
     * doesn't handle unpaired surrogates as a single character.
     */
    private var hasSurrogates = false

    /**
     * When hasSurrogates is true, delimiters are converted to code
     * points and isDelimiter(int) is used to determine if the given
     * codepoint is a delimiter.
     */
    private var delimiterCodePoints: IntArray? = null

    /**
     * Constructs a string tokenizer for the specified string. All
     * characters in the `delim` argument are the delimiters
     * for separating tokens.
     *
     *
     * If the `returnDelims` flag is `true`, then
     * the delimiter characters are also returned as tokens. Each
     * delimiter is returned as a string of length one. If the flag is
     * `false`, the delimiter characters are skipped and only
     * serve as separators between tokens.
     *
     *
     * Note that if `delim` is `null`, this constructor does
     * not throw an exception. However, trying to invoke other methods on the
     * resulting `StringTokenizer` may result in a
     * `NullPointerException`.
     *
     * @param   str            a string to be parsed.
     * @param   delim          the delimiters.
     * @param   returnDelims   flag indicating whether to return the delimiters
     * as tokens.
     * @exception NullPointerException if str is `null`
     */
    @JsName("StringTokenizer_initWithDelimetersAndReturnFlag")
    constructor(str: String, delim: String?, returnDelims: Boolean) {
        currentPosition = 0
        newPosition = -1
        delimsChanged = false
        this.str = str
        maxPosition = str.length
        delimiters = delim
        retDelims = returnDelims
        setMaxDelimCodePoint()
    }

    /**
     * Constructs a string tokenizer for the specified string. The
     * characters in the `delim` argument are the delimiters
     * for separating tokens. Delimiter characters themselves will not
     * be treated as tokens.
     *
     *
     * Note that if `delim` is `null`, this constructor does
     * not throw an exception. However, trying to invoke other methods on the
     * resulting `StringTokenizer` may result in a
     * `NullPointerException`.
     *
     * @param   str     a string to be parsed.
     * @param   delim   the delimiters.
     * @exception NullPointerException if str is `null`
     */
    @JsName("StringTokenizer_initWithDelimiters")
    constructor(str: String, delim: String): this(str, delim, false)

    /**
     * Constructs a string tokenizer for the specified string. The
     * tokenizer uses the default delimiter set, which is
     * `"&nbsp;&#92;t&#92;n&#92;r&#92;f"`: the space character,
     * the tab character, the newline character, the carriage-return character,
     * and the form-feed character. Delimiter characters themselves will
     * not be treated as tokens.
     *
     * @param   str   a string to be parsed.
     * @exception NullPointerException if str is `null`
     */
    @JsName("StringTokenizer_init")
    constructor(str: String): this(str, " \t\n\r\u000C", false)
    // Kotlin does not recognize '\f' (Form Feed / next page), convert to \u000C

    override fun hasNext(): Boolean {
        /*
         * Temporarily store this position and use it in the following
         * nextToken() method only if the delimiters haven't been changed in
         * that nextToken() invocation.
         */
        this.newPosition = skipDelimiters(this.currentPosition)
        return (this.newPosition < this.maxPosition)
    }

    override fun next(): String {
        /*
         * If next position already computed in hasMoreElements() and
         * delimiters have changed between the computation and this invocation,
         * then use the computed value.
         */
        this.currentPosition = if (this.newPosition >= 0 && !this.delimsChanged) this.newPosition
                else skipDelimiters(this.currentPosition)

        /* Reset these anyway */
        this.delimsChanged = false
        this.newPosition = -1

        if (this.currentPosition >= this.maxPosition) {
            throw NoSuchElementException()
        }
        val start = this.currentPosition
        this.currentPosition = scanToken(this.currentPosition)
        return this.str.substring(start, this.currentPosition)
    }

    /**
     * Returns the next token in this string tokenizer's string. First,
     * the set of characters considered to be delimiters by this
     * `StringTokenizer` object is changed to be the characters in
     * the string `delim`. Then the next token in the string
     * after the current position is returned. The current position is
     * advanced beyond the recognized token.  The new delimiter set
     * remains the default after this call.
     *
     * @param      delim   the new delimiters.
     * @return     the next token, after switching to the new delimiter set.
     * @exception  NoSuchElementException  if there are no more tokens in this
     * tokenizer's string.
     * @exception NullPointerException if delim is `null`
     */
    @JsName("nextWithDelimiters")
    fun next(delim: String): String {
        this.delimiters = delim

        /* delimiter string specified, so set the appropriate flag. */
        this.delimsChanged = true

        setMaxDelimCodePoint()
        return this.next()
    }

    /**
     * Calculates the number of times that this tokenizer's
     * `nextToken` method can be called before it generates an
     * exception. The current position is not advanced.
     *
     * @return  the number of tokens remaining in the string using the current
     * delimiter set.
     * @see .next(String)
     */
    @JsName("countToken")
    fun countTokens(): Int {
        var count = 0
        var currpos = this.currentPosition
        while (currpos < this.maxPosition) {
            currpos = skipDelimiters(currpos)
            if (currpos >= this.maxPosition) {
                break
            }
            currpos = scanToken(currpos)
            count += 1
        }
        return count
    }

    /**
     * Skips delimiters starting from the specified position. If retDelims
     * is false, returns the index of the first non-delimiter character at or
     * after startPos. If retDelims is true, startPos is returned.
     */
    private fun skipDelimiters(startPos: Int): Int {
        val delimiters = this.delimiters!!

        var position = startPos
        while (!this.retDelims && position < this.maxPosition) {
            if (!this.hasSurrogates) {
                val c = this.str[position]
                if (c.toInt() > this.maxDelimCodePoint || delimiters.indexOf(c) < 0) {
                    break
                }
                position += 1
            } else {
                val c = codePointAtInCharSequence(position, this.str)
                if (c > this.maxDelimCodePoint || !isDelimiter(c)) {
                    break
                }
                position += tech.firas.lang.Character.charCount(c)
            }
        }
        return position
    }

    /**
     * Skips ahead from startPos and returns the index of the next delimiter
     * character encountered, or maxPosition if no such delimiter is found.
     */
    private fun scanToken(startPos: Int): Int {
        var position = startPos
        while (position < this.maxPosition) {
            if (!this.hasSurrogates) {
                val c = this.str[position]
                if (c.toInt() <= this.maxDelimCodePoint && this.delimiters!!.indexOf(c) >= 0) {
                    break
                }
                position += 1
            } else {
                val c = codePointAtInCharSequence(position, this.str)
                if (c <= this.maxDelimCodePoint && isDelimiter(c)) {
                    break
                }
                position += tech.firas.lang.Character.charCount(c)
            }
        }
        if (this.retDelims && startPos == position) {
            if (!this.hasSurrogates) {
                val c = this.str[position]
                if (c.toInt() <= this.maxDelimCodePoint && this.delimiters!!.indexOf(c) >= 0) {
                    position += 1
                }
            } else {
                val c = codePointAtInCharSequence(position, this.str)
                if (c <= maxDelimCodePoint && isDelimiter(c)) {
                    position += tech.firas.lang.Character.charCount(c)
                }
            }
        }
        return position
    }

    private fun isDelimiter(codePoint: Int): Boolean {
        for (delimiterCodePoint in this.delimiterCodePoints!!) {
            if (delimiterCodePoint == codePoint) {
                return true
            }
        }
        return false
    }

    /**
     * Set maxDelimCodePoint to the highest char in the delimiter set.
     */
    private fun setMaxDelimCodePoint() {
        val delimiters = this.delimiters
        if (delimiters == null) {
            maxDelimCodePoint = 0
            return
        }

        var m = 0
        var count = 0
        run {
            var i = 0
            while (i < delimiters.length) {
                var c = delimiters[i].toInt()
                if (c >= tech.firas.lang.Character.MIN_HIGH_SURROGATE.toInt() && c <= tech.firas.lang.Character.MAX_LOW_SURROGATE.toInt()) {
                    c = codePointAtInCharSequence(i, delimiters)
                    hasSurrogates = true
                }
                if (m < c) {
                    m = c
                }
                count += 1
                i += tech.firas.lang.Character.charCount(c)
            }
        }
        this.maxDelimCodePoint = m

        if (this.hasSurrogates) {
            val delimCodePoints = IntArray(count)
            this.delimiterCodePoints = delimCodePoints
            var i = 0
            var j = 0
            while (i < count) {
                val c = codePointAtInCharSequence(i, delimiters)
                delimCodePoints[i] = c
                i += 1
                j += tech.firas.lang.Character.charCount(c)
            }
        }
    }
}