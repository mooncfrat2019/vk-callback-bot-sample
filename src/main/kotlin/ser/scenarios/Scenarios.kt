package ser.scenarios

import com.google.gson.Gson
import io.ktor.util.date.*
import ser.logging.logger
import ser.types.ButtonCore
import ser.types.Keyboard
import ser.types.TextButton
import ser.types.getPayload
import ser.vk.APIParams
import ser.vk.Api

var gson: Gson = Gson()

object Scenarios {
    fun start(from_id: Int?) {
        Api("messages", "send", APIParams(
            message = "Привет!",
            random_id = getTimeMillis(),
            peer_id = from_id,
            keyboard = gson.toJson(Keyboard(one_time = true, buttons = mutableListOf(
                listOf(
                    ButtonCore(
                        color = "secondary",
                        action = TextButton(
                            label = "Переключиться на бота",
                            payload = getPayload(type = "text", value = "useBot")
                        )
                    ),
                    ButtonCore(
                        color = "secondary",
                        action = TextButton(
                            label = "Написать админиам",
                            payload = getPayload(type = "text", value = "callMeTheBoss")
                        )
                    ),
                )
            )
            ))
        )
        ).request()
    }
    fun startWithBoss(from_id: Int?) {
        Api("messages", "send", APIParams(
            message = "Вы переключились на общение с администраторами сообщества.",
            random_id = getTimeMillis(),
            peer_id = from_id,
            keyboard = gson.toJson(Keyboard(one_time = true, buttons = mutableListOf(
                listOf(
                    ButtonCore(
                        color = "secondary",
                        action = TextButton(
                            label = "Переключиться на бота",
                            payload = getPayload(type = "text", value = "useBot")
                        )
                    ),
                )
            )
            ))
        )
        ).request()
    }
    fun startBot(from_id: Int?) {
        Api("messages", "send", APIParams(
            message = "Включен режим общения с ботом.",
            random_id = getTimeMillis(),
            peer_id = from_id,
            keyboard = gson.toJson(
                Keyboard(one_time = true,
                    buttons = mutableListOf(
                        listOf(
                            ButtonCore(
                                color = "primary",
                                action = TextButton(
                                    label = "Помощь",
                                    payload = getPayload(type = "text", value = "help")
                                )
                            ),
                            ButtonCore(
                                color = "primary",
                                action = TextButton(
                                    label = "Общение",
                                    payload = getPayload(type = "text", value = "talk")
                                )
                            ),
                        ),
                        listOf(
                            ButtonCore(
                                color = "secondary",
                                action = TextButton(
                                    label = "Вернуться в начало",
                                    payload = getPayload(type = "text", value = "menu")
                                    )
                                ),
                            )
                        )
                    ))
            )
        ).request()
    }
    fun unknown(from_id: Int?) {
        try {
            Api("messages", "send", APIParams(
                message = "Какая-то неизвестная команда",
                random_id = getTimeMillis(),
                peer_id = from_id,
                keyboard = gson.toJson(Keyboard(one_time = true, buttons = mutableListOf(
                    listOf(
                        ButtonCore(
                            color = "primary",
                            action = TextButton(
                                label = "Вернуться к началу",
                                payload = getPayload(type = "text", value = "start")
                            )
                        ),
                    )
                )
                ))
            )
            ).request()
        } catch (cause: Throwable) {
            logger.trace { cause }
            logger.error { cause }
            logger.error { "Error while call api" }
        }

    }
}
