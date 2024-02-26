package de.hglabor.mongo.extensions

import org.litote.kmongo.coroutine.*

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
suspend inline fun <reified T : Any> CoroutineDatabase.getOrCreateCollection(collectionName: String): CoroutineCollection<T> {
    if (cachedCollections.isEmpty()) cachedCollections.addAll(listCollectionNames())
    if (collectionName !in cachedCollections)
        createCollection(collectionName)
    return getCollection<T>(collectionName)
}