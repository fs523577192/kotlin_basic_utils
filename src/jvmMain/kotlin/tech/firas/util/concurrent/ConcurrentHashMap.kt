package tech.firas.util.concurrent

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 *
 * @author Wu Yuping
 */
actual fun <K: Any, V: Any> getConcurrentHashMap(
        keyClass: KClass<K>, valueClass: KClass<V>): MutableMap<K, V> {
    return ConcurrentHashMap()
}

actual fun <K: Any, V: Any> getConcurrentHashMap(
        keyClass: KClass<K>, valueClass: KClass<V>, initialCapacity: Int): MutableMap<K, V> {
    return ConcurrentHashMap(initialCapacity)
}

actual fun <K: Any, V: Any> getConcurrentHashMap(keyClass: KClass<K>, valueClass: KClass<V>,
        initialCapacity: Int, loadFactor: Float): MutableMap<K, V> {
    return ConcurrentHashMap(initialCapacity, loadFactor)
}

actual fun <K: Any, V: Any> getConcurrentHashMap(keyClass: KClass<K>, valueClass: KClass<V>,
        initialCapacity: Int, loadFactor: Float, concurrencyLevel: Int): MutableMap<K, V> {
    return ConcurrentHashMap(initialCapacity, loadFactor, concurrencyLevel)
}
