/*
 * Copyright 2002-2018 the original author or authors.
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

import org.firas.lang.getName
import org.firas.lang.isAssignableFrom
import kotlin.contracts.ExperimentalContracts
import kotlin.js.JsName
import kotlin.jvm.JvmStatic
import kotlin.reflect.KClass

/**
 * Assertion utility class that assists in validating arguments.
 *
 *
 * Useful for identifying programmer errors early and clearly at runtime.
 *
 *
 * For example, if the contract of a public method states it does not
 * allow `null` arguments, `Assert` can be used to validate that
 * contract. Doing this clearly indicates a contract violation when it
 * occurs and protects the class's invariants.
 *
 *
 * Typically used to validate method arguments rather than configuration
 * properties, to check for cases that are usually programmer errors rather
 * than configuration errors. In contrast to configuration initialization
 * code, there is usually no point in falling back to defaults in such methods.
 *
 *
 * This class is similar to JUnit's assertion library. If an argument value is
 * deemed invalid, an {@link IllegalArgumentException} is thrown (typically).
 * For example:
 *
 * ```
 * Assert.notNull(clazz, "The class must not be null");
 * Assert.isTrue(i > 0, "The value must be greater than zero");
 * ```
 *
 *
 * Mainly for internal use within the framework; consider
 * [Apache's Commons Lang](https://commons.apache.org/proper/commons-lang/)
 * for a more comprehensive suite of `String` utilities.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Colin Sampaleanu
 * @author Rob Harrop
 * @author Wu Yuping (migrate from Spring Framework)
 * @since Spring Framework 1.1.2
 */
abstract class Assert {
    // this class is abstract so that it cannot be instantiated

    @ExperimentalContracts
    companion object {
        /**
         * Assert a boolean expression, throwing an `IllegalStateException`
         * if the expression evaluates to `false`.
         *
         * Call [.isTrue] if you wish to throw an `IllegalArgumentException`
         * on an assertion failure.
         *
         * ```
         * Assert.state(id == null, "The id property must not already be initialized")
         * ```
         *
         * @param expression a boolean expression
         * @param message the exception message to use if the assertion fails
         * @throws IllegalStateException if `expression` is `false`
         */
        @JsName("state")
        @JvmStatic
        fun state(expression: Boolean, message: String) {
            if (!expression) {
                throw IllegalStateException(message)
            }
        }

        /**
         * Assert a boolean expression, throwing an `IllegalStateException`
         * if the expression evaluates to `false`.
         *
         * Call [.isTrue] if you wish to throw an `IllegalArgumentException`
         * on an assertion failure.
         *
         * ```
         * Assert.state(id == null) {"ID for " + entity.getName() + " must not already be initialized"}
         * ```
         *
         * @param expression a boolean expression
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalStateException if `expression` is `false`
         * @since Spring Framework 5.0
         */
        @JsName("stateWithMessageSupplier")
        @JvmStatic
        fun state(expression: Boolean, messageSupplier: (() -> String)?) {
            if (!expression) {
                throw IllegalStateException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert a boolean expression, throwing an `IllegalArgumentException`
         * if the expression evaluates to `false`.
         *
         * ```
         * Assert.isTrue(i > 0, "The value must be greater than zero")
         * ```
         *
         * @param expression a boolean expression
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if `expression` is `false`
         */
        @JsName("isTrue")
        @JvmStatic
        fun isTrue(expression: Boolean, message: String) {
            if (!expression) {
                throw IllegalArgumentException(message)
            }
        }

        /**
         * Assert a boolean expression, throwing an `IllegalArgumentException`
         * if the expression evaluates to `false`.
         *
         * ```
         * Assert.isTrue(i > 0) {"The value '$i' must be greater than zero")
         * ```
         *
         * @param expression a boolean expression
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if `expression` is `false`
         * @since Spring Framework 5.0
         */
        @JsName("isTrueWithMessageSupplier")
        @JvmStatic
        fun isTrue(expression: Boolean, messageSupplier: (() -> String)?) {
            if (!expression) {
                throw IllegalArgumentException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert that an object is `null`.
         *
         * ```
         * Assert.isNull(value, "The value must be null")
         * ```
         *
         * @param nullable the object to check
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if the object is not `null`
         */
        @JsName("isNull")
        @JvmStatic
        fun isNull(nullable: Any?, message: String) {
            if (nullable != null) {
                throw IllegalArgumentException(message)
            }
        }

        /**
         * Assert that an object is `null`.
         *
         * ```
         * Assert.isNull(value) {"The value '$value' must be null"}
         * ```
         *
         * @param nullable the object to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if the object is not `null`
         * @since Spring Framework 5.0
         */
        @JsName("isNullWithMessageSupplier")
        @JvmStatic
        fun isNull(nullable: Any?, messageSupplier: (() -> String)?) {
            if (nullable != null) {
                throw IllegalArgumentException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert that an object is not `null`.
         *
         * ```
         * Assert.notNull(clazz, "The class must not be null")
         * ```
         *
         * @param nullable the object to check
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if the object is `null`
         */
        @JsName("notNull")
        @JvmStatic
        fun notNull(nullable: Any?, message: String) {
            if (nullable == null) {
                throw IllegalArgumentException(message)
            }
        }

        /**
         * Assert that an object is not `null`.
         *
         * ```
         * Assert.notNull(clazz) {"The class '" + clazz.getName() + "' must not be null"}
         * ```
         *
         * @param nullable the object to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if the object is `null`
         * @since Spring Framework 5.0
         */
        @JsName("notNullWithMessageSupplier")
        @JvmStatic
        fun notNull(nullable: Any?, messageSupplier: (() -> String)?) {
            if (nullable == null) {
                throw IllegalArgumentException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert that the given String is not empty; that is,
         * it must not be `null` and not the empty String.
         *
         * ```
         * Assert.hasLength(name, "Name must not be empty")
         * ```
         *
         * @param text the String to check
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if the text is empty
         * @see StringUtils.hasLength
         */
        @JsName("hasLength")
        @JvmStatic
        fun hasLength(text: String?, message: String) {
            if (text.isNullOrEmpty()) {
                throw IllegalArgumentException(message)
            }
        }

        /**
         * Assert that the given String is not empty; that is,
         * it must not be `null` and not the empty String.
         *
         * ```
         * Assert.hasLength(name) {"Name for account '" + account.getId() + "' must not be empty"}
         * ```
         *
         * @param text the String to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if the text is empty
         * @since Spring Framework 5.0
         * @see StringUtils.hasLength
         */
        @JsName("hasLengthWithMessageSupplier")
        @JvmStatic
        fun hasLength(text: String?, messageSupplier: (() -> String)?) {
            if (text.isNullOrEmpty()) {
                throw IllegalArgumentException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert that the given String contains valid text content; that is, it must not
         * be `null` and must contain at least one non-whitespace character.
         *
         * ```
         * Assert.hasText(name, "'name' must not be empty")
         * ```
         *
         * @param text the String to check
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if the text does not contain valid text content
         * @see StringUtils.hasText
         */
        @JsName("hasText")
        @JvmStatic
        fun hasText(text: String?, message: String) {
            if (!StringUtils.hasText(text)) {
                throw IllegalArgumentException(message)
            }
        }

        /**
         * Assert that the given String contains valid text content; that is, it must not
         * be `null` and must contain at least one non-whitespace character.
         *
         * ```
         * Assert.hasText(name) {"Name for account '" + account.getId() + "' must not be empty"}
         * ```
         *
         * @param text the String to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if the text does not contain valid text content
         * @since Spring Framework 5.0
         * @see StringUtils.hasText
         */
        @JsName("hasTextWithMessageSupplier")
        @JvmStatic
        fun hasText(text: String?, messageSupplier: (() -> String)?) {
            if (!StringUtils.hasText(text)) {
                throw IllegalArgumentException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert that the given text does not contain the given substring.
         *
         * ```
         * Assert.doesNotContain(name, "rod", "Name must not contain 'rod'")
         * ```
         *
         * @param textToSearch the text to search
         * @param substring the substring to find within the text
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if the text contains the substring
         */
        @JsName("doesNotContain")
        @JvmStatic
        fun doesNotContain(textToSearch: String?, substring: String, message: String) {
            if (!textToSearch.isNullOrEmpty() && substring.isNotEmpty() &&
                textToSearch.contains(substring)
            ) {
                throw IllegalArgumentException(message)
            }
        }

        /**
         * Assert that the given text does not contain the given substring.
         *
         * ```
         * Assert.doesNotContain(name, forbidden) {"Name must not contain '$forbidden'"}
         * ```
         *
         * @param textToSearch the text to search
         * @param substring the substring to find within the text
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if the text contains the substring
         * @since Spring Framework 5.0
         */
        @JsName("doesNotContainWithMessageSupplier")
        @JvmStatic
        fun doesNotContain(textToSearch: String?, substring: String, messageSupplier: (() -> String)?) {
            if (!textToSearch.isNullOrEmpty() && substring.isNotEmpty() &&
                textToSearch.contains(substring)
            ) {
                throw IllegalArgumentException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert that an array contains elements; that is, it must not be
         * `null` and must contain at least one element.
         *
         * ```
         * Assert.notEmpty(array, "The array must contain elements")
         * ```
         *
         * @param array the array to check
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if the object array is `null` or contains no elements
         */
        @JsName("arrayNotEmpty")
        @JvmStatic
        fun notEmpty(array: Array<*>?, message: String) {
            if (array.isNullOrEmpty()) {
                throw IllegalArgumentException(message)
            }
        }

        /**
         * Assert that an array contains elements; that is, it must not be
         * `null` and must contain at least one element.
         *
         * ```
         * Assert.notEmpty(array) {"The $arrayType array must contain elements"}
         * ```
         *
         * @param array the array to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if the object array is `null` or contains no elements
         * @since Spring Framework 5.0
         */
        @JsName("arrayNotEmptyWithMessageSupplier")
        @JvmStatic
        fun notEmpty(array: Array<*>?, messageSupplier: (() -> String)?) {
            if (array.isNullOrEmpty()) {
                throw IllegalArgumentException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert that an array contains no `null` elements.
         *
         * Note: Does not complain if the array is empty!
         *
         * ```
         * Assert.noNullElements(array, "The array must contain non-null elements")
         * ```
         *
         * @param array the array to check
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if the object array contains a `null` element
         */
        @JsName("noNullElements")
        @JvmStatic
        fun noNullElements(array: Array<*>?, message: String) {
            if (array != null) {
                for (element in array) {
                    if (element == null) {
                        throw IllegalArgumentException(message)
                    }
                }
            }
        }

        /**
         * Assert that an array contains no `null` elements.
         *
         * Note: Does not complain if the array is empty!
         *
         * ```
         * Assert.noNullElements(array) {"The $arrayType array must contain non-null elements"}
         * ```
         *
         * @param array the array to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if the object array contains a `null` element
         * @since Spring Framework 5.0
         */
        @JsName("noNullElementsWithMessageSupplier")
        @JvmStatic
        fun noNullElements(array: Array<*>?, messageSupplier: (() -> String)?) {
            if (array != null) {
                for (element in array) {
                    if (element == null) {
                        throw IllegalArgumentException(messageSupplier?.invoke())
                    }
                }
            }
        }

        /**
         * Assert that a collection contains elements; that is, it must not be
         * `null` and must contain at least one element.
         *
         * ```
         * Assert.notEmpty(collection, "Collection must contain elements")
         * ```
         *
         * @param collection the collection to check
         * @param message the exception message to use if the assertion fails
         * @throws IllegalArgumentException if the collection is `null` or
         * contains no elements
         */
        @JsName("collectionNotEmpty")
        @JvmStatic
        fun notEmpty(collection: Collection<*>?, message: String) {
            if (collection.isNullOrEmpty()) {
                throw IllegalArgumentException(message)
            }
        }

        /**
         * Assert that a collection contains elements; that is, it must not be
         * `null` and must contain at least one element.
         *
         * ```
         * Assert.notEmpty(collection) {"The $collectionType collection must contain elements"}
         * ```
         *
         * @param collection the collection to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails
         * @throws IllegalArgumentException if the collection is `null` or
         * contains no elements
         * @since Spring Framework 5.0
         */
        @JsName("collectionNotEmptyWithMessageSupplier")
        @JvmStatic
        fun notEmpty(collection: Collection<*>?, messageSupplier: (() -> String)?) {
            if (collection.isNullOrEmpty()) {
                throw IllegalArgumentException(messageSupplier?.invoke())
            }
        }

        /**
         * Assert that the provided object is an instance of the provided class.
         * ```
         * Assert.instanceOf(Foo.class, foo, "Foo expected")
         * ```
         *
         * @param type the type to check against
         * @param obj the object to check
         * @param message a message which will be prepended to provide further context.
         * If it is empty or ends in ":" or ";" or "," or ".", a full exception message
         * will be appended. If it ends in a space, the name of the offending object's
         * type will be appended. In any other case, a ":" with a space and the name
         * of the offending object's type will be appended.
         * @throws IllegalArgumentException if the object is not an instance of type
         */
        @JsName("isInstanceOf")
        @JvmStatic
        fun isInstanceOf(type: KClass<*>, obj: Any, message: String) {
            notNull(type, "Type to check against must not be null")
            if (!type.isInstance(obj)) {
                instanceCheckFailed(type, obj, message)
            }
        }

        /**
         * Assert that the provided object is an instance of the provided class.
         * ```
         * Assert.instanceOf(Foo.class, foo, () -&gt; "Processing " + Foo.class.getSimpleName() + ":");
         * ```
         *
         * @param type the type to check against
         * @param obj the object to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails. See [.isInstanceOf] for details.
         * @throws IllegalArgumentException if the object is not an instance of type
         * @since 5.0
         */
        @JsName("isInstanceOfWithMessageSupplier")
        @JvmStatic
        fun isInstanceOf(type: KClass<*>, obj: Any, messageSupplier: (() -> String)?) {
            notNull(type, "Type to check against must not be null")
            if (!type.isInstance(obj)) {
                instanceCheckFailed(type, obj, messageSupplier?.invoke())
            }
        }

        /**
         * Assert that `superType.isAssignableFrom(subType)` is `true`.
         * ```
         * Assert.isAssignable(Number.class, myClass, "Number expected")
         * ```
         *
         * @param superType the super type to check against
         * @param subType the sub type to check
         * @param message a message which will be prepended to provide further context.
         * If it is empty or ends in ":" or ";" or "," or ".", a full exception message
         * will be appended. If it ends in a space, the name of the offending sub type
         * will be appended. In any other case, a ":" with a space and the name of the
         * offending sub type will be appended.
         * @throws IllegalArgumentException if the classes are not assignable
         */
        @JsName("isAssignable")
        @JvmStatic
        fun isAssignable(superType: KClass<*>, subType: KClass<*>?, message: String) {
            notNull(superType, "Super type to check against must not be null")
            if (subType == null || !superType.isAssignableFrom(subType)) {
                assignableCheckFailed(superType, subType, message)
            }
        }

        /**
         * Assert that `superType.isAssignableFrom(subType)` is `true`.
         * ```
         * Assert.isAssignable(Number.class, myClass, () -&gt; "Processing " + myAttributeName + ":");
         * ```
         *
         * @param superType the super type to check against
         * @param subType the sub type to check
         * @param messageSupplier a supplier for the exception message to use if the
         * assertion fails. See [.isAssignable] for details.
         * @throws IllegalArgumentException if the classes are not assignable
         * @since 5.0
         */
        @JsName("isAssignableWithMessageSupplier")
        @JvmStatic
        fun isAssignable(superType: KClass<*>, subType: KClass<*>?, messageSupplier: (() -> String)?) {
            notNull(superType, "Super type to check against must not be null")
            if (subType == null || !superType.isAssignableFrom(subType)) {
                assignableCheckFailed(superType, subType, messageSupplier?.invoke())
            }
        }

        @JvmStatic
        private fun instanceCheckFailed(type: KClass<*>, obj: Any?, msg: String?) {
            val className = "_" // obj?.javaClass?.getName() ?: "null"
            var result = ""
            var defaultMessage = true
            if (!msg.isNullOrEmpty()) {
                if (endsWithSeparator(msg)) {
                    result = "$msg "
                } else {
                    result = messageWithTypeName(msg, className)
                    defaultMessage = false
                }
            }
            if (defaultMessage) {
                result = result + "Object of class [$className] must be an instance of $type"
            }
            throw IllegalArgumentException(result)
        }

        @JvmStatic
        private fun assignableCheckFailed(superType: KClass<*>, subType: KClass<*>?, msg: String?) {
            var result = ""
            var defaultMessage = true
            if (!msg.isNullOrEmpty()) {
                if (endsWithSeparator(msg)) {
                    result = "$msg "
                } else {
                    result = messageWithTypeName(msg, subType)
                    defaultMessage = false
                }
            }
            if (defaultMessage) {
                result += (subType?.getName() + " is not assignable to " + superType.getName())
            }
            throw IllegalArgumentException(result)
        }

        @JvmStatic
        private fun endsWithSeparator(msg: String): Boolean {
            return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."))
        }

        @JvmStatic
        private fun messageWithTypeName(msg: String, typeName: Any?): String {
            return msg + (if (msg.endsWith(" ")) "" else ": ") + typeName
        }
    }
}
