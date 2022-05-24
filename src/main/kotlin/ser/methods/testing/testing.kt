package ser.methods.testing

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ser.objects.Outging

fun testing(): Any {
    return Outging.Response(Json.decodeFromString("{ \"test\": \"test\" }"))
}