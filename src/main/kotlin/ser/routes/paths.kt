package ser.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ser.types.ExtendedIncoming
import ser.utils.getMethod
import ser.utils.resolveType

fun Route.routesList() {
    route("/", HttpMethod.Get) {
        handle {
            call.respondText("Imtyplder", contentType = ContentType.Text.Plain)
        }

    }

    route("/api/{method}", HttpMethod.Get) {
        handle {
            val method = call.parameters["method"]
            fun getMethodResult(): Any {
                return getMethod(method.toString())
            }

            call.respond(getMethodResult())
        }
    }

    route("/callback", HttpMethod.Post) {
        handle {
            val body = call.receive<ExtendedIncoming>()
            resolveType(body)
            call.respond("ok")
        }
    }
}
