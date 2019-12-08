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
