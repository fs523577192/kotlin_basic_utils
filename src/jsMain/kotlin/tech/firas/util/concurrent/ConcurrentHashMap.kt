/*
 * Copyright 2019-2019 the original author or authors.
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
package tech.firas.util.concurrent

import kotlin.reflect.KClass

/**
 *
 * @author Wu Yuping
 */
@JsName("getConcurrentHashMap")
actual fun <K: Any, V: Any> getConcurrentHashMap(
        keyClass: KClass<K>, valueClass: KClass<V>): MutableMap<K, V> {
    return HashMap()
}

@JsName("getConcurrentHashMapWithInitialCapacity")
actual fun <K: Any, V: Any> getConcurrentHashMap(
        keyClass: KClass<K>, valueClass: KClass<V>, initialCapacity: Int): MutableMap<K, V> {
    return HashMap(initialCapacity)
}

@JsName("getConcurrentHashMapWithInitialCapacityAndLoadFactor")
actual fun <K: Any, V: Any> getConcurrentHashMap(keyClass: KClass<K>, valueClass: KClass<V>,
        initialCapacity: Int, loadFactor: Float): MutableMap<K, V> {
    return HashMap(initialCapacity, loadFactor)
}

@JsName("getConcurrentHashMapWithInitialCapacityLoadFactorAndConcurrencyLevel")
actual fun <K: Any, V: Any> getConcurrentHashMap(keyClass: KClass<K>, valueClass: KClass<V>,
        initialCapacity: Int, loadFactor: Float, concurrencyLevel: Int): MutableMap<K, V> {
    return HashMap(initialCapacity, loadFactor)
}
