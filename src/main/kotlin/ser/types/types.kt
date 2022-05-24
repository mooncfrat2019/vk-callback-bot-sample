package ser.types

import com.google.gson.annotations.SerializedName
import com.typesafe.config.Optional
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ser.Global.API_VERSION
import ser.Global.DEFAULT_LABEL
import ser.Global.DEFAULT_LINK
import ser.Global.DEFAULT_VKAPPS_HASH
import ser.Global.DEFAULT_VKAPPS_ID
import ser.Global.DEFAULT_VKAPPS_OWNER_ID
import ser.Global.DEFAULT_VKPAY_HASH
import ser.Global.callbackSecretKey
import ser.Global.groupId
import java.io.Serializable as SerializableJava

@Serializable
open class BaseIncoming (
    @Required val type: String,
    @Required val group_id: Int = groupId,
)

var EVENT_TYPES_MESSAGES: List<String> = mutableListOf(
    "message_new",
    "message_edit",
    "message_reply",
    "message_allow",
    "message_deny",
    "message_typing_state",
    "message_event",
)

fun getEventType(type: String): String { return EVENT_TYPES_MESSAGES.single { item -> item == type } }

@Serializable
data class ExtendedIncoming (
    val incomingType: String = "message_new",
    val v: String = API_VERSION,
    val secret: String = callbackSecretKey,
    @SerializedName("object")
    @Optional val `object`: MessageEventUniversal? = null,
): BaseIncoming(type = getEventType(incomingType))

@Serializable
data class PhotoSizes (
    val type: String?,
    val url: String?,
    val width: Int?,
    val height: Int?,
)


@Serializable
data class ViedoCoverImage (
    val with_padding: Int,
    val url: String,
    val width: Int,
    val height: Int,
)

@Serializable
data class ViedoFirstFrameImage (
    val url: String,
    val width: Int,
    val height: Int,
)

@Serializable
data class Likes (
    val count: Int,
    val user_likes: Int,
)

@Serializable
data class DocumentPreviewPhoto (
    val sizes: List<PhotoSizes>,
)


@Serializable
data class Graffiti (
    val src: String?,
    val width: Int?,
    val height: Int?,
)

@Serializable
data class AudioMessage (
    val duratation: Int?,
    val waveform: List<Int>?,
    val link_ogg: String,
    val link_mp3: String,
)

@Serializable
data class DocumentPreview (
    val photo: DocumentPreviewPhoto,
    val graffiti: Graffiti,
    val audio_message: AudioMessage,
)

@Serializable
data class Reposts (
    val count: Int,
    val wall_count: Int,
    val mail_count: Int,
    val user_reposted: Int,
)

@Serializable
data class Photo (
    val id: Int,
    val album_id: Int,
    val owner_id: Int,
    val user_id: Int,
    val text: String,
    val date: Int,
    val sizes: PhotoSizes,
    val width: Int,
    val height: Int,
)

@Serializable
data class Audio (
    val id: Int,
    val owner_id: Int,
    val artist: String,
    val title: String,
    val duration: Int,
    val url: String,
    val lyrics_id: Int,
    val album_id: Int,
    val genre_id: Int,
    val date: Int,
    val no_search: Int,
    val is_hq: Int,
)

@Serializable
data class Video (
    val id: Int,
    val owner_id: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val image: ViedoCoverImage,
    val first_frame: ViedoFirstFrameImage,
    val date: Int,
    val adding_date: Int,
    val views: Int,
    val local_views: Int,
    val comments: Int,
    val player: String,
    val platform: String,
    val can_add: Int,
    val is_private: Int,
    val access_key: String? = null,
    val processing: Int,
    val is_favorite: Int,
    val can_comment: Int,
    val can_edit: Int,
    val can_like: Int,
    val can_repost: Int,
    val can_subscribe: Int,
    val can_add_to_faves: Int,
    val can_attach_link: Int,
    val width: Int,
    val height: Int,
    val user_id: Int,
    val converting: Int,
    val added: Int,
    val is_subscribed: Int,
    val repeat: Int,
    val type: String,
    val balance: Int,
    val live_status: String,
    val live: Int,
    val upcoming: Int,
    val spectators: Int,
    val likes: Likes,
    val reposts: Reposts,
)

@Serializable
data class Document (
    val id: Int,
    val owner_id: Int,
    val title: String,
    val size: Int,
    val ext: String,
    val url: String,
    val date: Int,
    val type: String,
    val preview: DocumentPreview,
)

@Serializable
data class Sticker (
    val product_id: Int,
    val sticker_id: Int,
    val images: PhotoSizes,
    val images_with_background: PhotoSizes,
    val animation_url: String,
    val is_allowed: Boolean,
)


@Serializable
data class Coordintaes (
    val latitude: Int,
    val longitude: Int,
)

@Serializable
data class Place (
    val id: Int,
    val title: String,
    val latitude: Int,
    val longitude: Int,
    val icon: String,
    val country: String,
    val city: String,
    /* Если место добавлено как чекин в сообщество, объект place имеет дополнительные поля: */
    @Optional val type: String? = null,
    @Optional val group_id: Int? = null,
    @Optional val group_photo: String? = null,
    @Optional val checkins: Int? = null,
    @Optional val updated: Int? = null,
    @Optional val address: Int? = null,
)

@Serializable
data class GeoPoint (
    val type: String,
    val coordinates: Coordintaes,
    val place: Place,
)


@Serializable
data class ButtonAction (
    val type: String,
    val url: String,
)

@Serializable
data class AttachedUrl (
    val url: String,
    val title: String,
    val caption: String,
    val description: String,
    @Optional val button: Photo? = null,
)

@Serializable
data class Attachments (
    val type: String,
    @Optional val photo: Photo? = null,
    @Optional val video: Video? = null,
    @Optional val audio_message: AudioMessage? = null,
    @Optional val audio: Audio? = null,
    @Optional val geo: GeoPoint? = null,
    @Optional val document: Document? = null,
    @Optional val attachedUrl: AttachedUrl? = null,
    @Optional val sticker: Sticker? = null,
    @Optional val place: Place? = null,
)

/*
@Serializable
data class ColorPrimary(val primary: String? =  "#5181B8")
@Serializable
data class ColorSecondary(val secondary: String? =  "#FFFFFF")
@Serializable
data class ColorNegative(val negative: String? =  "#E64646")
@Serializable
data class ColorPositive(val positive: String? =  "#4BB34B")
*/

/*
@Serializable
data class Colors(
    @Optional val primary: String? =  "#5181B8",
    @Optional val secondary: String? =  "#FFFFFF",
    @Optional val negative: String? =  "#E64646",
    @Optional val positive: String? =  "#4BB34B",
)*/

@Serializable
data class Keyboard (
    @SerialName("one_time")
    val one_time: Boolean,
    @SerialName("buttons")
    val buttons: MutableList<List<ButtonCore>> = mutableListOf(emptyList()),
    @SerialName("inline")
    @Optional val inline: Boolean? = null,
)

@Serializable
open class Button(
    @SerialName("type")
    @Required open val type: String,
)

@Serializable
data class ButtonCore(
    @SerialName("action")
    val action: Button = mutableListOf(
        TextButton(),
        OpenLinkButton(),
        LocationButton(),
        VKPayButton(),
        VKAppsButton(),
        CallbackButton(),
    ).first(),
    @SerialName("color")
    @Optional val color: String? =  mutableListOf(
        "primary",
        "secondary",
        "negative",
        "positive"
    ).first(),
) : SerializableJava

fun getPayload (type: String, value: String): String { return "{ \"$type\":\"$value\" }" }

@Serializable
data class Payload(
   val text: String? = null,
   val link: String? = null,
   val location: String? = null,
   val vkpay: String? = null,
   val open_app: String? = null,
   val callback: String? = null,
)

const val PAYLOAD_DEFAULT_VALUE = "start"

object TYPES {
   const val TEXT: String = "text"
   const val LINK: String = "link"
   const val LOCATION: String = "location"
   const val VKPAY: String = "vkpay"
   const val OPEN_APP: String = "open_app"
   const val CALLBACK: String = "callback"
}

@Serializable
data class TextButton(@Required val label: String = "Label", @Optional val payload: String? = null): Button(type = TYPES.TEXT)

@Serializable
data class OpenLinkButton(
    @Required val link: String  = DEFAULT_LINK,
    @Required val label: String  = DEFAULT_LABEL,
    @Optional val payload: String? = getPayload(TYPES.LINK, PAYLOAD_DEFAULT_VALUE),
): Button(type = TYPES.LINK)

@Serializable
data class LocationButton(
    @Optional val payload: String? = getPayload(TYPES.LOCATION, PAYLOAD_DEFAULT_VALUE),
): Button(type = TYPES.LOCATION)

@Serializable
data class VKPayButton(
    @Optional val payload: String? = getPayload(TYPES.VKPAY, PAYLOAD_DEFAULT_VALUE),
    @Required val hash: String = DEFAULT_VKPAY_HASH,
): Button(type = TYPES.VKPAY)

@Serializable
data class VKAppsButton(
    @Required val app_id: Int = DEFAULT_VKAPPS_ID,
    @Required val owner_id: Int = DEFAULT_VKAPPS_OWNER_ID,
    @Required val label: String = DEFAULT_LABEL,
    @Optional val hash: String? = DEFAULT_VKAPPS_HASH,
    @Optional val payload: String? = getPayload(TYPES.OPEN_APP, PAYLOAD_DEFAULT_VALUE),
): Button(type = TYPES.OPEN_APP)

@Serializable
data class CallbackButton(
    @Required val label: String? = DEFAULT_LABEL,
    @Optional val payload: String? = getPayload(TYPES.CALLBACK, PAYLOAD_DEFAULT_VALUE),
): Button(type = TYPES.CALLBACK)

@Serializable
data class PhotoSizesNamedList(
    @Optional val photo_50: String,
    @Optional val photo_100: String,
    @Optional val photo_200: String,
)

@Serializable
data class MessageAction(
    @Required val type: String,
    @Required val member_id: Int,
    @Optional val text: String? = null,
    @Optional val email: String? = null,
    @Optional val photo: PhotoSizesNamedList? = null,
)

@Serializable
data class MessageObject (
    val id: Int,
    val date: Int,
    val peer_id: Int,
    val from_id: Int,
    val text: String,
    val random_id: Int = 1,
    @Optional val ref: String? = null,
    @Optional val ref_source: String? = null,
    @Optional val attachments: List<Attachments>? = emptyList(),
    @Optional val important: Boolean? = null,
    @Optional val geo: GeoPoint? = null,
    @Optional val payload: String? = null,
    @Optional val keyboard: Keyboard? = null,
    @Optional val fwd_messages: List<MessageObject>? = null,
    @Optional val reply_message: MessageObject? = null,
    @Optional val action: MessageAction? = null,
    @Optional val admin_author_id: Int? = null,
    @Optional val conversation_message_id: Int? = null,
    @Optional val is_cropped: Boolean? = null,
    @Optional val members_count: Int? = null,
    @Optional val update_time: Int? = null,
    @Optional val was_listened: Boolean? = null,
    @Optional val pinned_at: Int? = null,
    @Optional val message_tag: String? = null,
)

@Serializable
data class ClientInfo(
    val button_actions: List<String> = mutableListOf(
        "text",
        "vkpay",
        "open_app",
        "location",
        "open_link",
        "callback",
        "intent_subscribe",
        "intent_unsubscribe"
    ),
    val keyboard: Boolean,
    val inline_keyboard: Boolean,
    val carousel: Boolean,
    val lang_id: Int,
)

@Serializable
data class MessageEventUniversal(
    @Required val message: MessageObject,
    @Optional val client_info: ClientInfo? = null,
)
