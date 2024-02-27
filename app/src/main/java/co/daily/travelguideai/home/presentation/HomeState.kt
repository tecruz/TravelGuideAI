package co.daily.travelguideai.home.presentation

import co.daily.travelguideai.home.domain.model.HomeFilterSettings
import co.daily.travelguideai.home.domain.model.Place
import co.daily.travelguideai.home.domain.model.Region

data class HomeState(
    val searchText: String = "",
    val showDialog: Boolean = false,
    val filterSettings: HomeFilterSettings = HomeFilterSettings(),
    val filterSettingsBackup: HomeFilterSettings = filterSettings,
    val chatReply: String? = null,
    val selectedRegion: Region = Region.TODAS,
    val popularPlaces: List<Place> = emptyList(),
    val popularPlacesBackup: List<Place> = popularPlaces,
    val isLoading: Boolean = false
)