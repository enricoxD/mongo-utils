package de.hglabor.mongo.cache

import java.util.Stack

/**
 * A first-in-first-out (FIFO) cache implementation.
 * This cache evicts the least recently used items when the maximum size is reached.
 * @param maxSize the maximum number of key-value pairs the cache can hold
 * @param onFlush a function that is invoked when the cache is flushed (cleared)
 */
class FifoCache<K, V>(val maxSize: Int, override val onFlush: ((K, V) -> Unit)? = null) : ClearingCache<K, V>() {
    private val keysByInsertionTime = Stack<K>()

    /**
     * Sets a key-value pair in the cache.
     * This method overrides the same method in the parent [ClearingCache] class and ensures that the least recently used items are evicted if the cache exceeds its maximum size.
     * @param key the key to set
     * @param value the value to set
     */
    override fun set(key: K, value: V) {
        keysByInsertionTime.remove(key)
        super.set(key, value)
        keysByInsertionTime.push(key)
    }

    /**
     * Tries to flush (clear) the cache by evicting the least recently used items until the size of the cache is less than or equal to the maximum size.
     */
    override fun tryFlush() {
        while (size > maxSize) {
            val keyToRemove = keysByInsertionTime.pop()
            remove(keyToRemove)
        }
    }
}
