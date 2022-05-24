package ser.validators

import ser.Global.callbackSecretKey
import ser.Global.groupId
import ser.types.ClientInfo

class Validators() {
    fun checkCallbackServer(secret: String, group_id: Int): Boolean {
        val isEqualSecret = secret == callbackSecretKey
        val isEqualGroup = group_id == groupId
        return (isEqualSecret && isEqualGroup)
    }

    fun canUseKeyboard(clientInfo: ClientInfo? = null): Boolean {
        return clientInfo?.keyboard!!
    }

    fun canUseInlineKeyboard(clientInfo: ClientInfo? = null): Boolean {
        return (clientInfo?.keyboard!! && clientInfo.inline_keyboard)
    }

    fun canUseButtonType(clientInfo: ClientInfo? = null, buttonType: String): Boolean {
        val checkIsExist = clientInfo?.button_actions?.find { it == buttonType }
        return checkIsExist.toBoolean()
    }
}
