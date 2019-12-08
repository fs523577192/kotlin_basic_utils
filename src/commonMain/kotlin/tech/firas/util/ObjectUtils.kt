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
import kotlin.jvm.JvmStatic

/**
 * Miscellaneous object utility methods.
 *
 *
 * Mainly for internal use within the framework.
 *
 *
 * Thanks to Alex Ruiz for contributing several enhancements to this class!
 *
 * @author Juergen Hoeller
 * @author Keith Donald
 * @author Rod Johnson
 * @author Rob Harrop
 * @author Chris Beams
 * @author Sam Brannen
 * @author Wu Yuping (migrate from Spring Framework)
 * @see ClassUtils
 * @see CollectionUtils
 * @see StringUtils
 */
abstract class ObjectUtils {

    companion object {

        /**
         * Check whether the given array of enum constants contains a constant with the given name.
         * @param enumValues the enum values to check, typically obtained via `MyEnum.values()`
         * @param constant the constant name to find (must not be null or empty string)
         * @param caseSensitive whether case is significant in determining a match
         * @return whether the constant has been found in the given array
         */
        @JsName("containsConstant")
        @JvmStatic
        fun containsConstant(enumValues: Array<Enum<*>>, constant: String, caseSensitive: Boolean = false): Boolean {
            for (candidate in enumValues) {
                val compareResult = if (caseSensitive) candidate.toString() == constant
                        else candidate.toString().equals(constant, ignoreCase = true)
                if (compareResult) {
                    return true
                }
            }
            return false
        }

        private const val INITIAL_HASH = 7
        private const val MULTIPLIER = 31

        private const val EMPTY_STRING = ""
        private const val NULL_STRING = "null"
        private const val ARRAY_START = "{"
        private const val ARRAY_END = "}"
        private const val EMPTY_ARRAY = ARRAY_START + ARRAY_END
        private const val ARRAY_ELEMENT_SEPARATOR = ", "
    }
}

/**
 * Determine whether the given array is empty:
 * i.e. `null` or of zero length.
 * @param array the array to check
 * @see .isEmpty
 */
@ExperimentalContracts
@JsName("Array_isNullOrEmpty")
inline fun Array<*>?.isNullOrEmpty(): Boolean {
    contract {
        returns(false) implies (this@isNullOrEmpty != null)
    }
    return this == null || this.isEmpty()
}

/**
 * Check whether the given array contains the given element.
 * @param array the array to check (may be `null`,
 * in which case the return value will always be `false`)
 * @param element the element to check for
 * @return whether the element has been found in the given array
 */
@ExperimentalContracts
@JsName("Array_contains")
inline fun Array<*>?.contains(element: Any?): Boolean {
    contract {
        returns(true) implies (this@contains != null)
    }
    if (null == this) {
        return false
    }
    for (arrayEle in this) {
        if (arrayEle == element) {
            return true
        }
    }
    return false
}
