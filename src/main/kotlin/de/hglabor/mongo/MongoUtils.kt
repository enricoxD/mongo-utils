package de.hglabor.mongo

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import de.hglabor.mongo.config.MongoConfigFile
import de.hglabor.mongo.config.MongoCredentials
import de.hglabor.mongo.database.MongoConnection
import kotlinx.coroutines.*
import java.io.File

object MongoUtils {
    var database: MongoDatabase? = null
    var coroutineScope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    /**
     * Establishes a connection to the MongoDB database using the provided credentials
     *
     * @param credentials You credentials to log into your database
     * **/
    suspend fun connect(credentials: MongoCredentials): MongoDatabase? {
        database = MongoConnection.establish(credentials)
        return database
    }

    /**
     * Establishes a connection to the MongoDB database using the provided configuration file
     *
     * @param configFile The configuration file containing the json representation of your [de.hglabor.mongo.config.MongoCredentials]
     * **/
    suspend fun connect(configFile: File): MongoDatabase? {
        val mongoConfig = MongoConfigFile(configFile)
        val credentials = mongoConfig.read()
        return connect(credentials)
    }
}

/**
 * Asynchronously executes the provided suspendable [runnable] function within a coroutine scope,
 * returning a [Deferred] representing the result of the operation.
 *
 * @param runnable The suspendable function to be executed asynchronously.
 * @return A [Deferred] representing the result of the asynchronous operation.
 *
 * @see kotlinx.coroutines.async
 * @see MongoUtils.coroutineScope
 */
fun <T> async(runnable: suspend () -> T): Deferred<T> {
    return MongoUtils.coroutineScope.async {
        runCatching { runnable() }
            .onFailure { it.printStackTrace() }
            .getOrThrow()
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
fun launchScope(runnable: suspend () -> Unit) {
    MongoUtils.coroutineScope.launch {
        runCatching { runnable() }
            .onFailure { it.printStackTrace() }
            .getOrThrow()
    }
}
