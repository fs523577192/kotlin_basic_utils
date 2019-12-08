/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
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

/*
 *******************************************************************************
 * Copyright (C) 2010, International Business Machines Corporation and         *
 * others. All Rights Reserved.                                                *
 *******************************************************************************
 */
package tech.firas.util

import kotlin.js.JsName

/**
 *
 * @author Wu Yuping
 */
/*internal*/ class UnicodeLocaleExtension: LocaleExtension {

    companion object {
        internal const val SINGLETON = 'u'

        internal val CA_JAPANESE = UnicodeLocaleExtension("ca", "japanese")
        internal val NU_THAI = UnicodeLocaleExtension("nu", "thai")

        @JsName("isSingletonChar")
        internal fun isSingletonChar(c: Char): Boolean {
            return SINGLETON == LocaleUtils.toLower(c)
        }

        @JsName("isAttribute")
        internal fun isAttribute(s: String): Boolean {
            // 3*8alphanum
            val len = s.length
            return len in 3..8 && LocaleUtils.isAlphaNumericString(s)
        }

        @JsName("isKey")
        internal fun isKey(s: String): Boolean {
            // 2alphanum
            return s.length == 2 && LocaleUtils.isAlphaNumericString(s)
        }

        @JsName("isTypeSubtag")
        internal fun isTypeSubtag(s: String): Boolean {
            // 3*8alphanum
            val len = s.length
            return len in 3..8 && LocaleUtils.isAlphaNumericString(s)
        }
    }

    private val attributes: Set<String>
    private val keywords: Map<String, String>

    private constructor(key: String, value: String): super(SINGLETON, key + '-' + value) {
        this.attributes = HashSet(1, 1f)

        val temp = HashMap<String, String>(1, 1f)
        temp[key] = value
        this.keywords = temp
    }

    internal constructor(attributes: Set<String>?, keywords: Map<String, String>?): super(SINGLETON) {
        this.attributes = attributes ?: HashSet(1, 1f)
        this.keywords = keywords ?: HashMap(1, 1f)
        if (this.attributes.isNotEmpty() || this.keywords.isNotEmpty()) {
            val sj = ArrayList<String>()
            sj.addAll(this.attributes)
            for (entry in this.keywords.entries) {
                val key = entry.key
                val value = entry.value

                sj.add(key)
                if (value.isNotEmpty()) {
                    sj.add(value)
                }
            }
            setValue(sj.joinToString(LanguageTag.SEP))
        }
    }

    @JsName("getUnicodeLocaleAttributes")
    internal fun getUnicodeLocaleAttributes(): Set<String> {
        return this.attributes
    }

    @JsName("getUnicodeLocaleKeys")
    internal fun getUnicodeLocaleKeys(): Set<String> {
        return this.keywords.keys
    }

    @JsName("getUnicodeLocaleType")
    internal fun getUnicodeLocaleType(unicodeLocaleKey: String): String? {
        return this.keywords[unicodeLocaleKey]
    }
}