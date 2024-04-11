package de.hglabor.mongo.extensions

import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.onEach

val cachedCollections by lazy { hashSetOf<String>() }

/**
 * Retrieves or creates a MongoDB collection with the specified collection name.
 *
 * This function retrieves an existing MongoDB collection with the given name from the database,
 * or creates a new collection if it does not already exist.
 *
 * @param collectionName The name of the collection to retrieve or create.
 * @return The [CoroutineCollection] with the specified name.
 */

suspend inline fun <reified T : Any> MongoDatabase.getOrCreateCollection(collectionName: String): MongoCollection<T> {
    if (cachedCollections.isEmpty()) {
        listCollectionNames().onEach { collection ->
            cachedCollections.add(collection)
        }
    }
    if (collectionName !in cachedCollections)
        createCollection(collectionName)
    return getCollection<T>(collectionName)
}