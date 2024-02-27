package co.daily.travelguideai.home.data.remote.dto


import com.squareup.moshi.Json

data class Choice(
    @Json(name = "finish_reason")
    val finishReason: String,
    @Json(name = "index")
    val index: Int,
    @Json(name = "logprobs")
    val logprobs: Any,
    @Json(name = "message")
    val message: Message
)