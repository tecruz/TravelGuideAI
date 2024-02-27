package co.daily.travelguideai.home.data.remote.dto


import com.squareup.moshi.Json

data class Usage(
    @Json(name = "completion_tokens")
    val completionTokens: Int,
    @Json(name = "prompt_tokens")
    val promptTokens: Int,
    @Json(name = "total_tokens")
    val totalTokens: Int
)