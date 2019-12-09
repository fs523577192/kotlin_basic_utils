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

/**
 *
 * @author Wu Yuping
 */
/*internal*/ abstract class LocaleObjectCache<K, V> {

    private val map: MutableMap<K, CacheEntry<K, V>>

    constructor(initialCapacity: Int, loadFactor: Float) {
        this.map = HashMap(initialCapacity, loadFactor)
    }

    constructor(): this(16, 0.75f)

    fun get(key: K): V? {
        var value: V? = null
        var entry = this.map[key]
        if (null != entry) {
            value = entry.value
        }
        if (null == value) {
            val _key = normalizeKey(key)
            val newValue = createObject(_key)
            if (null == _key || null == newValue) {
                // subclass must return non-null key/value object
                return null
            }

            val newEntry: CacheEntry<K, V> = CacheEntry(_key, newValue)
            entry = map.putIfAbsent(_key, newEntry)

            if (null == entry) {
                value = newValue
            } else {
                value = entry.value
                if (null == value) {
                    map[_key] = newEntry
                    value = newValue
                }
            }
        }
        return value
    }

    internal fun put(key: K, value: V): V? {
        val entry = CacheEntry(key, value)
        val oldEntry = this.map.put(key, entry)
        return oldEntry?.value
    }

    protected abstract fun createObject(key: K): V?

    protected fun normalizeKey(key: K): K {
        return key
    }

    companion object {
        private class CacheEntry<K, V>(
                internal val key: K, internal val value: V)
    }
}
