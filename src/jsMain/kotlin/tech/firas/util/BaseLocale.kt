/*
 * Copyright (c) 2010, 2018, Oracle and/or its affiliates. All rights reserved.
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
 * Copyright (C) 2009-2010, International Business Machines Corporation and    *
 * others. All Rights Reserved.                                                *
 *******************************************************************************
 */
package tech.firas.util

import kotlin.js.JsName

/**
 *
 * @author Wu Yuping (migrate from OpenJDK 11)
 */
/*internal*/ class BaseLocale {

    companion object {
        internal const val SEP = "_"

        private val CACHE = Cache()

        // Called for creating the Locale.* constants. No argument
        // validation is performed.
        @JsName("createInstance")
        fun createInstance(language: String, region: String): BaseLocale {
            val base = BaseLocale(language, "", region, "", false)
            CACHE.put(BaseLocaleKey(base), base)
            return base
        }

        @JsName("getInstance")
        fun getInstance(language: String?, script: String,
                        region: String, variant: String): BaseLocale? {
            var _language = language
            // JDK uses deprecated ISO639.1 language codes for he, yi and id
            if (_language != null) {
                if (LocaleUtils.caseIgnoreMatch(_language, "he")) {
                    _language = "iw"
                } else if (LocaleUtils.caseIgnoreMatch(_language, "yi")) {
                    _language = "ji"
                } else if (LocaleUtils.caseIgnoreMatch(_language, "id")) {
                    _language = "in"
                }
            }

            val key = BaseLocaleKey(_language, script, region, variant, false)
            return CACHE.get(key)
        }

        private fun normalizeBaseLocaleKey(key: BaseLocaleKey): BaseLocaleKey {
            if (key.normalized) {
                return key
            }

            // Only normalized keys may be softly referencing the data holder
            val locale = key.holder!!
            return BaseLocaleKey(
                locale.language, locale.script,
                locale.region, locale.variant, true
            )
        }

        private class Cache: LocaleObjectCache<BaseLocaleKey, BaseLocale>() {

            override fun createObject(key: BaseLocaleKey): BaseLocale? {
                return normalizeBaseLocaleKey(key).getBaseLocale()
            }
        }

        internal class BaseLocaleKey {

            internal val holder: BaseLocale?

            internal val normalized: Boolean
            private val hash: Int

            /**
             * Creates a Key. language and region must be normalized
             * (intern'ed in the proper case).
             */
            internal constructor(locale: BaseLocale) {
                this.holder = locale
                this.normalized = true
                val language = locale.language
                val region = locale.region
                if (
                    internString(LocaleUtils.toLowerString(language)) !== language
                    || internString(LocaleUtils.toUpperString(region)) !== region
                    || locale.variant != ""
                    || locale.script != ""
                ) {
                    throw AssertionError()
                }

                var h = language.hashCode()
                if (region != "") {
                    val len = region.length
                    for (i in 0 until len) {
                        h = 31 * h + LocaleUtils.toLower(region[i]).toInt()
                    }
                }
                this.hash = h
            }

            internal constructor(
                language: String?,
                script: String?,
                region: String?,
                variant: String?,
                normalize: Boolean
            ) {
                val _language = language ?: ""
                val _script = script ?: ""
                val _region = region ?: ""
                val _variant = variant ?: ""

                val locale = BaseLocale(_language, _script, _region, _variant, normalize)
                this.normalized = normalize
                this.holder = locale
                this.hash = hashCode(locale)
            }

            private fun hashCode(locale: BaseLocale): Int {
                var h = 0
                val lang = locale.language
                var len = lang.length
                for (i in 0 until len) {
                    h = 31 * h + LocaleUtils.toLower(lang[i]).toInt()
                }
                val scrt = locale.script
                len = scrt.length
                for (i in 0 until len) {
                    h = 31 * h + LocaleUtils.toLower(scrt[i]).toInt()
                }
                val regn = locale.region
                len = regn.length
                for (i in 0 until len) {
                    h = 31 * h + LocaleUtils.toLower(regn[i]).toInt()
                }
                val vart = locale.variant
                len = vart.length
                for (i in 0 until len) {
                    h = 31 * h + vart[i].toInt()
                }
                return h
            }

            internal fun getBaseLocale(): BaseLocale? {
                return this.holder
            }

            override fun hashCode(): Int {
                return this.hash
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) {
                    return true
                }
                if (other is BaseLocaleKey && this.hash == other.hash) {
                    val _otherBaseLocale = other.getBaseLocale()!!
                    val locale = this.getBaseLocale()
                    if (locale != null
                        && LocaleUtils.caseIgnoreMatch(_otherBaseLocale.language, locale.language)
                        && LocaleUtils.caseIgnoreMatch(_otherBaseLocale.script, locale.script)
                        && LocaleUtils.caseIgnoreMatch(_otherBaseLocale.region, locale.region)
                        // variant is case sensitive in JDK!
                        && _otherBaseLocale.variant == locale.variant
                    ) {
                        return true
                    }
                }
                return false
            }
        } // internal class BaseLocaleKey
    } // companion object

    internal val language: String
    internal val script: String
    internal val region: String
    internal val variant: String

    internal constructor(language: String, script: String, region: String,
                         variant: String, normalize: Boolean) {
        if (normalize) {
            this.language = internString(LocaleUtils.toLowerString(language))
            this.script = internString(LocaleUtils.toTitleString(script))
            this.region = internString(LocaleUtils.toUpperString(region))
            this.variant = internString(variant)
        } else {
            this.language = language
            this.script = script
            this.region = region
            this.variant = variant
        }
    }

}
