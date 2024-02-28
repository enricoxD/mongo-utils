package de.hglabor.mongo.cache

/**
 * An abstract class representing a cache with clearing capabilities.
 * This class extends [Cache] and provides additional functionality for clearing the cache.
 */
abstract class ClearingCache<K, V>: Cache<K, V>() {

    /**
     * A function that is invoked when the cache is flushed (cleared).
     * It takes two parameters: key and value.
     */
    abstract val onFlush: ((K, V) -> Unit)?

    /**
     * Sets a key-value pair in the cache.
     * This method overrides the same method in the parent [Cache] class and ensures that the cache is flushed before setting the new key-value pair.
     * @param key the key to set
     * @param value the value to set
     */
    override fun set(key: K, value: V) {
        tryFlush()
        super.set(key, value)
    }

    /**
     * Retrieves the value associated with the specified key from the cache.
     * This method overrides the same method in the parent [Cache] class and ensures that the cache is flushed before getting the value.
     * @param key the key to look up
     * @return the value associated with the specified key, or null if the key is not found
     */
    override fun get(key: K): V? {
        val value = super.get(key)
        tryFlush()
        return value
    }

    /**
     * Tries to flush (clear) the cache.
     * Subclasses must implement this method to define the behavior of cache flushing.
     */
    abstract fun tryFlush()
}
