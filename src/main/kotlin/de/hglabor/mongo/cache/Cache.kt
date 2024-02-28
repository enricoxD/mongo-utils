package de.hglabor.mongo.cache

/**
 * A simple generic cache implementation that stores key-value pairs.
 */
open class Cache<K, V> {
    internal val cache = HashMap<K, V>()

    /**
     * Retrieves the value associated with the specified key from the cache.
     * @param key the key whose associated value is to be retrieved
     * @return the value associated with the specified key, or null if the key is not found in the cache
     */
    open operator fun get(key: K): V? = cache[key]

    /**
     * Associates the specified value with the specified key in the cache.
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    open operator fun set(key: K, value: V) = cache.set(key, value)

    /**
     * Removes the mapping for the specified key from the cache if it is present.
     * @param key the key whose mapping is to be removed from the cache
     * @return the previous value associated with the specified key, or null if there was no mapping for the key
     */
    fun remove(key: K): V? = cache.remove(key)

    /**
     * Removes all key-value mappings from the cache.
     */
    fun clear() = cache.clear()

    /**
     * Performs the given action for each key-value pair in the cache.
     * @param callback the action to be performed for each key-value pair
     */
    fun forEach(callback: (key: K, value: V) -> Unit) {
        cache.forEach(callback)
    }

    /**
     * Returns the number of key-value pairs in the cache.
     */
    val size get() = cache.size
}
