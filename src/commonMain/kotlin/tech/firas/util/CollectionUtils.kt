/*
 * Copyright (c) 1997, 2018, Oracle and/or its affiliates. All rights reserved.
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
package tech.firas.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.js.JsName

/**
 * Miscellaneous collection utility methods.
 * Mainly for internal use within the framework.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Arjen Poutsma
 * @author Wu Yuping (migrate from Spring Framework)
 * @since Spring Framework 1.1.3
 */


/**
 * If the specified key is not already associated with a value (or is mapped
 * to `null`) associates it with the given value and returns `null`,
 * else returns the current value.
 *
 * @implSpec
 * The default implementation is equivalent to, for this `map`:
 *
 * ```
 * V v = map.get(key);
 * if (v == null)
 *     v = map.put(key, value);
 *
 * return v;
 * }
 * ```
 *
 *
 * The default implementation makes no guarantees about synchronization
 * or atomicity properties of this method. Any implementation providing
 * atomicity guarantees must override this method and document its
 * concurrency properties.
 *
 * @param key key with which the specified value is to be associated
 * @param value value to be associated with the specified key
 * @return the previous value associated with the specified key, or
 *         {@code null} if there was no mapping for the key.
 *         (A {@code null} return can also indicate that the map
 *         previously associated {@code null} with the key,
 *         if the implementation supports null values.)
 * @throws UnsupportedOperationException if the {@code put} operation
 *         is not supported by this map
 *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
 * @throws ClassCastException if the key or value is of an inappropriate
 *         type for this map
 *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
 * @throws NullPointerException if the specified key or value is null,
 *         and this map does not permit null keys or values
 *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
 * @throws IllegalArgumentException if some property of the specified key
 *         or value prevents it from being stored in this map
 *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
 * @since Java 1.8
 */
@JsName("MutableMap_putIfAbsent")
inline fun <K, V> MutableMap<K, V>.putIfAbsent(key: K, value: V): V? {
    return this[key] ?: this.put(key, value)
}

/**
 * Check whether the given Iterator contains the given element.
 * @param iterator the Iterator to check
 * @param element the element to look for
 * @return `true` if found, `false` otherwise
 */
@ExperimentalContracts
@JsName("Iterator_contains")
inline fun Iterator<*>?.contains(element: Any?): Boolean {
    contract {
        returns(true) implies (this@contains != null)
    }
    if (null != this) {
        while (this.hasNext()) {
            if (this.next() == element) {
                return true
            }
        }
    }
    return false
}

/**
 * Return `true` if any element in '`candidates`' is
 * contained in '`source`'; otherwise returns `false`.
 * @param source the source Collection
 * @param candidates the candidates to search for
 * @return whether any of the candidates has been found
 */
@ExperimentalContracts
@JsName("Collection_containsAny")
inline fun Collection<*>?.containsAny(candidates: Collection<*>?): Boolean {
    if (this.isNullOrEmpty() || candidates.isNullOrEmpty()) {
        return false
    }
    for (candidate in candidates) {
        if (this.contains(candidate)) {
            return true
        }
    }
    return false
}
