/*
 *
 *  * Copyright 2024 tecruz
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package co.travelguide.home.data

import co.travelguide.home.data.remote.ChatgptApi
import co.travelguide.home.data.remote.dto.ChatRequestDto
import co.travelguide.home.data.remote.dto.Message
import co.travelguide.home.domain.HomeRepository
import co.travelguide.home.domain.model.HomeFilterSettings
import co.travelguide.home.domain.model.Place
import co.travelguide.home.domain.model.Region

class HomeRepositoryImpl(
    private val api: ChatgptApi
) : HomeRepository {
    override suspend fun getTravelGuide(
        location: String,
        settings: HomeFilterSettings
    ): Result<String> {
        return try {
            var places = ""
            if (settings.restaurant) places += "Restaurantes, "
            if (settings.museums) places += "Museus, "

            val placesToVisit = if (places.isNotEmpty()) "e quero visitar: $places" else ""

            val request = ChatRequestDto(
                maxTokens = 1500,
                model = "gpt-3.5-turbo",
                messages = listOf(
                    Message(
                        content = "Você é um guia de viagem. Vou lhe dar minha localização e você vai sugerir lugares para visitar nas proximidades. Também vou dar os tipos de lugares que quero visitar. Além disso, quero que você sugira lugares semelhantes aos que mencionei e que sejam próximos ao meu primeiro local. Estou em $location $placesToVisit. Só quero os preços de cada lugar em euros. Classifique-os por tipo de lugar. Não repita os lugares.",
                        role = "user"
                    ),
                ),
                temperature = 0.7
            )
            val information = api.getTravelInformation(request)
            Result.success(information.choices.first().message.content)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPopularPlaces(): Result<List<Place>> {
        return Result.success(
            listOf(
                Place("USA", "New York", Region.AMERICA, "https://i.natgeofe.com/k/5b396b5e-59e7-43a6-9448-708125549aa1/new-york-statue-of-liberty_2x1.jpg"),
                Place("Argentina", "Buenos Aires", Region.AMERICA, "https://toposmagazine.com/wp-content/uploads/2021/10/1-Von-Deensel_Puerto_Madero_Buenos_Aires_40689219792-min-2.jpg"),
                Place("España", "Barcelona", Region.EUROPA, "https://www.fodors.com/wp-content/uploads/2022/03/Hero-UPDATEBarcelona-iStock-1320014700-1.jpg"),
                Place("Australia", "Sydney", Region.OCEANIA, "https://images.squarespace-cdn.com/content/v1/55ee34aae4b0bf70212ada4c/1577545161018-1F9Z9ZZQG9JO2O4WCWQX/keith-zhu-qaNcz43MeY8-unsplash+%281%29.jpg"),
                Place("Japan", "Tokyo", Region.ASIA, "https://www.gotokyo.org/en/plan/tokyo-outline/images/main.jpg"),
                Place("Italia", "Roma", Region.EUROPA, "https://q5s9m2q9.rocketcdn.me/wp-content/uploads/coliseu-roma-1140x757.jpg")
            )
        )
    }
}