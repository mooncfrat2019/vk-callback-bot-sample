package ser.vk

import com.typesafe.config.Optional
import com.vk.api.sdk.objects.annotations.Required
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ser.Global.ACCESS_TOKEN_MESSAGES
import ser.Global.API_VERSION
import ser.Global.VK_API_HOST
import java.net.HttpURLConnection
import java.net.URL
import kotlin.reflect.full.memberProperties

class Api(methodGroup: String, methodName: String, private val paramsObject: APIParams)  {
    @Contextual
    private val buildStingFromObject = paramsObject::class.memberProperties
        .filter { it.call(paramsObject) != null }
        .joinToString("&") { it.name + "=" + it.call(paramsObject) }
        .replace(" ", "%20")
    private val url = "https://$VK_API_HOST/method/$methodGroup.$methodName?access_token=$ACCESS_TOKEN_MESSAGES&v=$API_VERSION&$buildStingFromObject"
    /*
    fun logger() {
        println(url)
    }*/

    fun request() {
        sendGet(url)
    }
}

@Serializable
data class APIParams(
    /*Required*/
    @Required val message: String,
    @Required val random_id: Long,
    @Required val peer_id: Int?,
    /*Optional*/
    @Optional val keyboard: String? = null,
)

fun sendGet(url: String) {
    with(URL(url).openConnection() as HttpURLConnection) {
        requestMethod = "GET"  // optional default is GET

        println("\nSent 'GET' request. Response Code : $responseCode")

        inputStream.bufferedReader().use {
            it.lines().forEach { line ->
                println("response")
                println(line)
            }
        }
    }
}
