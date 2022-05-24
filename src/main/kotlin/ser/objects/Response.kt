package ser.objects

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.Serializable as Serializable

@Serializable
sealed class Outging() {
    abstract val response: JsonObject?

    @Serializable
    data class Response(override val response: JsonObject?): Outging()
}
