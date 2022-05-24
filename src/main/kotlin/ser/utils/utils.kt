package ser.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ser.callback.CallbackTypes
import ser.logging.logger
import ser.methods.Methods
import ser.types.ExtendedIncoming
import ser.types.Payload
import kotlin.reflect.full.memberProperties

fun getMethod(methodName: String): Any {
    val callableMethod = Methods::class.members.single { it.name == methodName }
    val methods = Methods()
    return callableMethod.call(methods)!!
}

fun resolveType(body: ExtendedIncoming): String {
    return try {
        println(body.type)
        val callableMethod = CallbackTypes::class.members.single { it.name == body.type }
        val cbTypes = CallbackTypes(body)
        callableMethod.call(cbTypes).toString()
        "ok"
    } catch (cause: Throwable) {
        logger.trace { cause }
        logger.error { cause }
        logger.error { "Error while resolve type" }
        "ok"
    }
}

fun resolveCommand(body: ExtendedIncoming): String {
    val payload: String? = body.`object`?.message?.payload
    println("payload")
    println(payload)
    return if (payload !== null) {
        val payloadObject = Json.decodeFromString<Payload>(payload.toString())
        val payloadElement = payloadObject::class.memberProperties
            .filter { it.call(payloadObject) != null }
            .map { it.call(payloadObject) }
        println("payloadElement")
        println(payloadElement)
        payloadElement.joinToString()
    } else {
        println("Can't resolve command")
        return "unknown"
    }
}
