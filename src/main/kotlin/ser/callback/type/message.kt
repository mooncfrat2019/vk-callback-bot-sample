package ser.callback.type

import ser.db.UseDB
import ser.db.UseDB.recordCurrentState
import ser.scenarios.Scenarios
import ser.types.*
import ser.utils.resolveCommand
import ser.logging.logger
import ser.objects.Payloads


object Message {
    /*Объект методов для обработки событий по их типам для группы Messages*/
    fun new(body: ExtendedIncoming): String {
        val fromId = body.`object`?.message?.from_id ?: 0
        val userDataFromDB = UseDB.usersGet(fromId)
        println("userDataFromDB")
        println(userDataFromDB)

        val newMessage = body.`object`?.message
        println("newMessage")
        println(newMessage)

        val command = resolveCommand(body)
        println("command")
        println(command)

        try {
            if (command == "unknown") {
                if (userDataFromDB.current_state == 0) {
                    Scenarios.start(fromId)
                    return "ok"
                }
                Scenarios.unknown(fromId)
            }

            if (command == "callMeTheBoss") {
                Scenarios.startWithBoss(fromId)
                recordCurrentState(fromId, Payloads.callMeTheBoss)
            }

            if (command == "useBot") {
                Scenarios.startBot(fromId)
                recordCurrentState(fromId, Payloads.useBot)
            }

            if (command == "menu") {
                Scenarios.start(fromId)
                recordCurrentState(fromId, Payloads.menu)
            }

        } catch (cause: Throwable) {
            logger.trace { cause }
            logger.error { cause }
            logger.error { "Error while run scenario" }
        }


        return "ok"
    }
}