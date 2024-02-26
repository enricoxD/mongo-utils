package de.hglabor.mongo.config

import java.io.File
import java.lang.Exception


class MongoConfigFile(val file: File) {
    private var rawFileContent: String

    init {
        if (!file.exists()) {
            if (!file.parentFile.exists()) file.parentFile.mkdirs()
            file.createNewFile()
        }
        rawFileContent = file.readText()
        if (rawFileContent.isBlank()) {
            writeDefaultAndThrow()
        }
    }

    /**
     * Writes default configuration to the file and throws an exception.
     *
     * This function writes default [MongoCredentials] to the file and throws an exception indicating
     * that the configuration file is invalid and has been set to default.
     *
     * @throws Exception if the configuration file is invalid and set to default.
     */
    private fun writeDefaultAndThrow() {
        val jsonCredentials = CredentialsParser.encode(MongoCredentials.defaultCredentials)
        file.writeText(jsonCredentials)
        throw Exception("Invalid config file: Config has been set to default.")
    }

    /**
     * Reads MongoDB credentials from the configuration file.
     *
     * @return The [MongoCredentials] read from the configuration file.
     */
    fun read() = CredentialsParser.decode(rawFileContent)
}