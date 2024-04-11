package de.hglabor.mongo.repository

import com.mongodb.kotlin.client.coroutine.MongoCollection

abstract class AbstractRepository<T: Any> {
    abstract val collection: MongoCollection<T>
}