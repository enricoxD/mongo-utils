package de.hglabor.mongo.extensions

import com.mongodb.client.model.Filters
import org.bson.conversions.Bson
import kotlin.reflect.KProperty1

/**
 * Extension function to create an equality filter.
 *
 * @param value The value to compare against.
 * @return The created Bson filter.
 */
infix fun <T, V> KProperty1<T, V>.eq(value: V): Bson {
    return Filters.eq(this.name, value)
}

/**
 * Extension function to create a "not in" filter.
 *
 * @param values The list of values to check against.
 * @return The created Bson filter.
 */
infix fun <T, V> KProperty1<T, V>.notIn(values: Iterable<V>): Bson {
    return Filters.nin(this.name, values)
}

/**
 * Extension function to create a regular expression filter.
 *
 * @param pattern The de.hglabor.mongo.extensions.regex pattern to match against.
 * @return The created Bson filter.
 */
infix fun <T> KProperty1<T, String>.regex(pattern: String): Bson {
    return Filters.regex(this.name, pattern)
}
