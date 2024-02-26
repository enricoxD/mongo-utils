package de.hglabor.mongo.config

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CredentialsParser {
    private val JSON = Json { prettyPrint = true }

    /**
     * Decodes the JSON string representation of MongoDB credentials into a [MongoCredentials] object.
     *
     * @param content The JSON string representation of MongoDB credentials.
     * @return The decoded [MongoCredentials].
     */
    fun decode(content: String): MongoCredentials {
        return JSON.decodeFromString<MongoCredentials>(content)
    }

    /**
     * Encodes the [MongoCredentials] into a JSON string representation.
     *
     * @param credentials The [MongoCredentials] to encode.
     * @return The JSON string representation.
     */
    fun encode(credentials: MongoCredentials): String {
        return JSON.encodeToString(credentials)
    }
}