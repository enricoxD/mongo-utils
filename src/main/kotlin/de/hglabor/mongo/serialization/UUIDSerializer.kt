package de.hglabor.mongo.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.nio.ByteBuffer
import java.util.*

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("de.hglabor.mongo.serialization.UUIDSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        val byteBuffer = ByteBuffer.allocate(16)
        byteBuffer.putLong(value.mostSignificantBits)
        byteBuffer.putLong(value.leastSignificantBits)
        encoder.encodeSerializableValue(ByteArraySerializer(), byteBuffer.array())
    }

    override fun deserialize(decoder: Decoder): UUID {
        val byteArray = decoder.decodeSerializableValue(ByteArraySerializer())
        val byteBuffer = ByteBuffer.wrap(byteArray)
        val mostSigBits = byteBuffer.long
        val leastSigBits = byteBuffer.long
        return UUID(mostSigBits, leastSigBits)
    }
}
