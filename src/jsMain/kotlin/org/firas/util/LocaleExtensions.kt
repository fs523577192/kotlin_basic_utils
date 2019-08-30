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
package org.firas.util

import kotlin.js.JsName

/**
 *
 * @author Wu Yuping (migrate from OpenJDK 11)
 */
internal class LocaleExtensions {

    internal val id: String
    private val extensionMap: Map<Char, LocaleExtension>

    private constructor(id: String, key: Char, value: LocaleExtension) {
        this.id = id
        val map = HashMap<Char, LocaleExtension>(1, 1f)
        map[key] = value
        this.extensionMap = map
    }

    // internal constructor(extensions: Map<>)

    @JsName("getKeys")
    internal fun getKeys(): Set<Char> {
        return if (this.extensionMap.isEmpty()) HashSet(1, 1f)
                else this.extensionMap.keys
    }

    @JsName("getExtension")
    internal fun getExtension(key: Char): LocaleExtension? {
        return this.extensionMap[LocaleUtils.toLower(key)]
    }

    @JsName("getExtensionValue")
    internal fun getExtensionValue(key: Char): String? {
        return this.extensionMap[LocaleUtils.toLower(key)]?.getValue()
    }

    @JsName("getUnicodeLocaleAttributes")
    internal fun getUnicodeLocaleAttributes(): Set<String> {
        val ext = extensionMap.get(UnicodeLocaleExtension.SINGLETON)
        if (null == ext) {
            return HashSet()
        }
        if (ext !is UnicodeLocaleExtension) {
            throw AssertionError()
        }
        return ext.getUnicodeLocaleAttributes()
    }

    @JsName("getUnicodeLocaleKeys")
    internal fun getUnicodeLocaleKeys(): Set<String> {
        val ext = extensionMap.get(UnicodeLocaleExtension.SINGLETON)
        if (null == ext) {
            return HashSet()
        }
        if (ext !is UnicodeLocaleExtension) {
            throw AssertionError()
        }
        return ext.getUnicodeLocaleKeys()
    }

    @JsName("getUnicodeLocaleType")
    internal fun getUnicodeLocaleType(unicodeLocaleKey: String): String? {
        val ext = extensionMap.get(UnicodeLocaleExtension.SINGLETON)
        if (null == ext) {
            return null
        }
        if (ext !is UnicodeLocaleExtension) {
            throw AssertionError()
        }
        return ext.getUnicodeLocaleType(LocaleUtils.toLowerString(unicodeLocaleKey))
    }

    @JsName("isEmpty")
    internal fun isEmpty(): Boolean {
        return this.extensionMap.isEmpty()
    }

    override fun toString(): String {
        return this.id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is LocaleExtensions) {
            return false
        }
        return this.id == other.id
    }

    companion object {
        val CALENDAR_JAPANESE = LocaleExtensions(
            "u-ca-japanese",
            UnicodeLocaleExtension.SINGLETON,
            UnicodeLocaleExtension.CA_JAPANESE
        )

        val NUMBER_THAI = LocaleExtensions(
            "u-nu-thai",
            UnicodeLocaleExtension.SINGLETON,
            UnicodeLocaleExtension.NU_THAI
        )

        @JsName("isValidKey")
        internal fun isValidKey(c: Char): Boolean {
            return LanguageTag.isExtensionSingletonChar(c) || LanguageTag.isPrivateusePrefixChar(c)
        }

        private fun toID(map: Map<Char, LocaleExtension>): String {
            val buf = StringBuilder()
            var privuse: LocaleExtension? = null
            for (entry in map.entries) {
                val singleton = entry.key
                val extension = entry.value
                if (LanguageTag.isPrivateusePrefixChar(singleton)) {
                    privuse = extension
                } else {
                    if (buf.isNotEmpty()) {
                        buf.append(LanguageTag.SEP)
                    }
                    buf.append(extension)
                }
            }
            if (privuse != null) {
                if (buf.isNotEmpty()) {
                    buf.append(LanguageTag.SEP)
                }
                buf.append(privuse)
            }
            return buf.toString()
        }
    }
}