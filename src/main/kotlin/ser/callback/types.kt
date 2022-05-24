package ser.callback

import ser.callback.type.Message
import ser.callback.type.confirmation
import ser.types.ExtendedIncoming

class CallbackTypes(body: ExtendedIncoming) {
    val confirmation: String = confirmation(body)
    val message_new: String = Message.new(body)
}