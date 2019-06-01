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
 * Return `true` if the supplied Collection is `null` or empty.
 * Otherwise, return `false`.
 * @param collection the Collection to check
 * @return whether the given Collection is empty
 */
@ExperimentalContracts
@JsName("Collection_isNullOrEmpty")
inline fun Collection<*>?.isNullOrEmpty(): Boolean {
    contract {
        returns(false) implies (this@isNullOrEmpty != null)
    }
    return (this == null || this.isEmpty())
}

/**
 * Return `true` if the supplied Map is `null` or empty.
 * Otherwise, return `false`.
 * @param map the Map to check
 * @return whether the given Map is empty
 */
@ExperimentalContracts
@JsName("Map_isNullOrEmpty")
inline fun Map<*, *>.isNullOrEmpty(): Boolean {
    contract {
        returns(false) implies (this@isNullOrEmpty != null)
    }
    return (this == null || this.isEmpty())
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
