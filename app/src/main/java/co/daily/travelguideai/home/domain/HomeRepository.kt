package co.daily.travelguideai.home.domain

import co.daily.travelguideai.home.domain.model.HomeFilterSettings
import co.daily.travelguideai.home.domain.model.Place

interface HomeRepository {
    suspend fun getTravelGuide(location: String, settings: HomeFilterSettings): Result<String>

    suspend fun getPopularPlaces(): Result<List<Place>>
}