package de.hglabor.mongo.repository

import de.hglabor.mongo.cache.Cache

abstract class CachedRepository<K, V: Any>: AbstractRepository<V>() {
    abstract val cache: Cache<K, V>
}