package co.daily.travelguideai.home.data.remote

import co.daily.travelguideai.home.data.remote.dto.ChatRequestDto
import co.daily.travelguideai.home.data.remote.dto.ChatResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatgptApi {
    companion object {
        const val BASE_URL = "https://api.openai.com/v1/chat/"
    }

    @POST("completions")
    suspend fun getTravelInformation(@Body body: ChatRequestDto): ChatResponseDto
}