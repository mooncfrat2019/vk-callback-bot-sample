package ser

import io.github.cdimascio.dotenv.dotenv
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import ser.Global.SERVER_PORT
import ser.db.DB
import ser.routes.routesList

val dotenv = dotenv()
fun getIntEnv(ENV_NAME: String, DEFAULT: String): Int {
    val localEnv = dotenv[ENV_NAME] ?: System.getenv(ENV_NAME) ?: DEFAULT
    return localEnv.toInt()
}
fun getStringEnv(ENV_NAME: String, DEFAULT: String, useDefaul: Boolean? = null): String {
    if (useDefaul == true) return DEFAULT
    return System.getenv(ENV_NAME) ?: dotenv[ENV_NAME] ?: DEFAULT
}
object Global {
    var groupId: Int = getIntEnv("GROUP_ID", "0")
    var API_VERSION: String = getStringEnv("API_VERSION", "5.131")
    var confirmationCode: String = getStringEnv("CONFIRMATION_CODE", "")
    var callbackSecretKey: String = getStringEnv("CALLBACK_SECRET", "")
    var ACCESS_TOKEN_MESSAGES: String = getStringEnv("ACCESS_TOKEN_MESSAGES", "")
    val DEFAULT_LABEL: String = "Label"
    val DEFAULT_LINK: String = "https://vk.com"
    val DEFAULT_VKPAY_HASH: String = "action=transfer-to-group&group_id=${groupId}&aid=10"
    val DEFAULT_VKAPPS_HASH: String = "#default"
    val DEFAULT_VKAPPS_ID: Int = 1
    val DEFAULT_VKAPPS_OWNER_ID: Int = 1
    val DB_PASSWORD: String = getStringEnv("DB_PASSWORD", "")
    val DB_HOST: String = getStringEnv("DB_HOST", "http://localhost")
    val DB_PORT: String = getStringEnv("DB_PORT", "")
    val DB_USER: String = getStringEnv("DB_USER", "")
    val DB_NAME: String = getStringEnv("DB_NAME", "")
    val SERVER_PORT: Int = getIntEnv("SERVER_PORT", "5000")
    val VK_API_HOST: String = "api.vk.com"
}

fun main() {
    DB.init()
    embeddedServer(Netty, port = SERVER_PORT) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                encodeDefaults = true
                ignoreUnknownKeys = true
            })
        }
        routing {
            routesList()
        }
    }.start(wait = true)
}
