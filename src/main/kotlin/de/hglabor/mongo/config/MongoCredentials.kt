package de.hglabor.mongo.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class MongoCredentials(
    val host: String,
    val port: Int,
    val username: String,
    @SerialName("password")
    private val rawPassword: String,
    val database: String
) {
    companion object {
        /**
         * Parses the raw password for use in the MongoDB connection URI.
         *
         * @param rawPassword The raw password to parse.
         * @return The parsed password.
         */
        fun parsePassword(rawPassword: String) = rawPassword.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25")

        val defaultCredentials by lazy { MongoCredentials("localhost", 27017, "username", "password", "admin") }
    }

    @Transient
    private val password = parsePassword(rawPassword)

    /**
     * Generates the MongoDB connection URI.
     *
     * @return The MongoDB connection URI.
     */
    val uri get() = "mongodb://$username:$password@$host:$port/$database"
}
