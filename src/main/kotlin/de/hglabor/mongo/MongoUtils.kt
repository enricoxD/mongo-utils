package de.hglabor.mongo

import de.hglabor.mongo.config.MongoConfigFile
import de.hglabor.mongo.database.MongoConnection
import org.litote.kmongo.coroutine.CoroutineDatabase
import java.io.File

object MongoUtils {
    var database: CoroutineDatabase? = null

    /**
     * Establishes a connection to the MongoDB database using the provided configuration file
     *
     * @param configFile The configuration file containing the json representation of your [de.hglabor.mongo.config.MongoCredentials]
     * **/
    suspend fun connect(configFile: File): CoroutineDatabase? {
        val mongoConfig = MongoConfigFile(configFile)
        val credentials = mongoConfig.read()
        database = MongoConnection.establish(credentials)
        return database
    }
}