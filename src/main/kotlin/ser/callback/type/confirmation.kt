package ser.callback.type

import ser.Global.confirmationCode
import ser.Global.groupId
import ser.types.ExtendedIncoming

fun confirmation(body: ExtendedIncoming): String {
    return if (body.group_id == groupId) confirmationCode else "Wrong group id"
}