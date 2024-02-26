package de.hglabor.mongo

import de.hglabor.mongo.config.MongoConfigFile
import de.hglabor.mongo.database.MongoConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.CoroutineDatabase
import java.io.File

object MongoUtils {
    var database: CoroutineDatabase? = null
    var coroutineScope = CoroutineScope(Dispatchers.IO)

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

/**
 * Asynchronously executes the provided suspendable [runnable] function within a coroutine scope.
 *
 * @param runnable The suspendable function to be executed asynchronously.
 *
 * @see kotlinx.coroutines.launch
 * @see MongoUtils.coroutineScope
 */
fun async(runnable: suspend () -> Unit) {
    MongoUtils.coroutineScope.launch {
        runnable()
    }
}
