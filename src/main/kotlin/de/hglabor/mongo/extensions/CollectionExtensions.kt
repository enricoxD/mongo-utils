package de.hglabor.mongo.extensions

import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.BsonDocument
import org.bson.conversions.Bson

suspend inline fun <reified T : Any> MongoCollection<T>.updateOne(filter: Bson, data: T) {
    val json = Json.encodeToString(data)
    val bson = BsonDocument.parse(json)
    this.updateOne(filter, bson)
}