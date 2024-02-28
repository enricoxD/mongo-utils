package de.hglabor.mongo.cache

import java.time.Duration

/**
 * A cache with time-based clearing capabilities.
 * This cache automatically flushes its contents based on a specified flush interval.
 * @param flushInterval the [Duration] after which the cache should be cleared
 * @param onFlush a function to be invoked when the cache is flushed (cleared)
 */
class TimedCache<K, V>(
    val flushInterval: Duration,
    override val onFlush: ((K, V) -> Unit)?
) : ClearingCache<K, V>() {

    private val lastFlush = System.currentTimeMillis()

    /**
     * Tries to flush (clear) the cache based on the specified flush interval.
     */
    override fun tryFlush() {
        if (System.nanoTime() - lastFlush >= flushInterval.toNanos()) clear()
    }
}
