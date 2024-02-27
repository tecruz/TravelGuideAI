package co.daily.travelguideai.home.data.remote.dto


import com.squareup.moshi.Json

data class Message(
    @Json(name = "content")
    val content: String,
    @Json(name = "role")
    val role: String
)