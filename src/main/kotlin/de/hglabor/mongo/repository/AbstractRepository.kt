package de.hglabor.mongo.repository

import org.litote.kmongo.coroutine.CoroutineCollection

abstract class AbstractRepository<T: Any> {
    abstract val collection: CoroutineCollection<T>
}