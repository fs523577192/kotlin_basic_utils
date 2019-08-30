package org.firas.util

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
