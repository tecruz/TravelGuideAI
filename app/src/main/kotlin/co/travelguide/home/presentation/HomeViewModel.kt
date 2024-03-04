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

package co.travelguide.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.travelguide.home.domain.HomeRepository
import co.travelguide.home.domain.model.Region
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            repository.getPopularPlaces().onSuccess {
                state = state.copy(
                    popularPlaces = it,
                    popularPlacesBackup = it
                )
            }.onFailure {
                println("Hubo un error")
            }
        }
    }

    fun search() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.getTravelGuide(state.searchText, state.filterSettings).onSuccess {
                state = state.copy(
                    chatReply = it
                )
            }.onFailure {
                println("Hubo un error")
            }
            state = state.copy(
                isLoading = false
            )
        }
    }

    fun onSettingsChange(action: HomeFilterDialogAction) {
        when (action) {
            HomeFilterDialogAction.OnApplyClick -> {
                state = state.copy(
                    filterSettingsBackup = state.filterSettings,
                    showDialog = false
                )
            }
            HomeFilterDialogAction.OnMuseumClick -> {
                state = state.copy(
                    filterSettings = state.filterSettings.copy(
                        museums = !state.filterSettings.museums
                    )
                )
            }
            HomeFilterDialogAction.OnPeopleMinus -> {
                state = state.copy(
                    filterSettings = state.filterSettings.copy(
                        people = state.filterSettings.people - 1
                    )
                )
            }
            HomeFilterDialogAction.OnPeoplePlus -> {
                state = state.copy(
                    filterSettings = state.filterSettings.copy(
                        people = state.filterSettings.people + 1
                    )
                )
            }
            HomeFilterDialogAction.OnRestaurantClick -> {
                state = state.copy(
                    filterSettings = state.filterSettings.copy(
                        restaurant = !state.filterSettings.restaurant
                    )
                )
            }
        }
    }

    fun onFilterClick() {
        state = state.copy(
            showDialog = true
        )
    }

    fun onFilterDismiss() {
        state = state.copy(
            showDialog = false,
            filterSettings = state.filterSettingsBackup
        )
    }

    fun onBackPress() {
        state = state.copy(
            chatReply = null
        )
    }

    fun onSearchTextChange(newText: String) {
        state = state.copy(
            searchText = newText
        )
    }

    fun onRegionSelect(region: Region) {
        state = state.copy(
            selectedRegion = region,
            popularPlaces = if (region != Region.TODAS) state.popularPlacesBackup.filter { it.region == region } else state.popularPlacesBackup
        )
    }
}