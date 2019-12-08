package tech.firas.util.concurrent

import kotlin.reflect.KClass

/**
 *
 * @author Wu Yuping
 */
expect fun <K: Any, V: Any> getConcurrentHashMap(
        keyClass: KClass<K>, valueClass: KClass<V>): MutableMap<K, V>

expect fun <K: Any, V: Any> getConcurrentHashMap(
        keyClass: KClass<K>, valueClass: KClass<V>, initialCapacity: Int): MutableMap<K, V>

expect fun <K: Any, V: Any> getConcurrentHashMap(keyClass: KClass<K>, valueClass: KClass<V>,
        initialCapacity: Int, loadFactor: Float): MutableMap<K, V>

expect fun <K: Any, V: Any> getConcurrentHashMap(keyClass: KClass<K>, valueClass: KClass<V>,
        initialCapacity: Int, loadFactor: Float, concurrencyLevel: Int): MutableMap<K, V>
