package de.hglabor.mongo.database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import de.hglabor.mongo.config.MongoCredentials
import org.bson.UuidRepresentation

/**
 * Singleton object responsible for establishing and managing connections to a MongoDB database.
 */
object MongoConnection {
    private lateinit var client: MongoClient

    /**
     * Establishes a connection to the MongoDB database using the provided credentials.
     *
     * This function creates a MongoDB client with the specified URI extracted from the credentials,
     * and returns a reference to the requested MongoDB database.
     *
     * @param credentials The [MongoCredentials] containing connection details.
     * @return The connected [MongoDatabase], or null if connection fails.
     */
    suspend fun establish(credentials: MongoCredentials): MongoDatabase? {
        runCatching {
            System.setProperty(
                "org.litote.mongo.test.mapping.service",
                "org.litote.kmongo.serialization.SerializationClassMappingTypeService"
            )
            client = MongoClient.create(
                MongoClientSettings.builder()
                    .applyConnectionString(ConnectionString(credentials.uri))
                    .uuidRepresentation(UuidRepresentation.STANDARD)
                    .build()
            )
            client.listDatabaseNames()
            client.getDatabase(credentials.database)
        }.onSuccess { database ->
            println("--------------------------")
            println("Successfully connected to MongoDB database")
            println("--------------------------")
            return database
        }.onFailure {
            println("--------------------------")
            println("Could not establish a connection to MongoDB database:")
            it.printStackTrace()
            println("--------------------------")
        }
        return null
    }

    /**
     * Closes the connection to the MongoDB database.
     *
     * @return True if the connection was successfully closed, false otherwise.
     */
    suspend fun disconnect(): Boolean {
        runCatching {
            client.close()
        }.onSuccess { database ->
            println("--------------------------")
            println("Successfully closed connection to MongoDB database")
            println("--------------------------")
            return true
        }.onFailure {
            println("--------------------------")
            println("Could not close connection to MongoDB database:")
            it.printStackTrace()
            println("--------------------------")
        }
        return false
    }
}