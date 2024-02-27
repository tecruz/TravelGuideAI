package co.daily.travelguideai.home.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.daily.travelguideai.home.presentation.components.HomeFilterButton
import co.daily.travelguideai.home.presentation.components.HomeFilterDialog
import co.daily.travelguideai.home.presentation.components.HomePopularFilter
import co.daily.travelguideai.home.presentation.components.HomePopularPlaceItem
import co.daily.travelguideai.home.presentation.components.HomeSearchBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (state.showDialog) {
        HomeFilterDialog(onDismiss = {
            viewModel.onFilterDismiss()
        }, filterSettings = state.filterSettings, onAction = {
            viewModel.onSettingsChange(it)
        })
    }

    BackHandler(state.chatReply != null) {
        viewModel.onBackPress()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(top = 32.dp)
    ) {
        item {
            Text(
                text = "Para onde viajas?",
                fontSize = 28.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeSearchBar(
                    onSearch = {
                        viewModel.search()
                    },
                    placeholder = "País, Cidade",
                    inputText = state.searchText,
                    onValueChange = { viewModel.onSearchTextChange(it) }
                )
                HomeFilterButton(onClick = { viewModel.onFilterClick() })
            }
        }

        if (state.isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        } else {
            state.chatReply?.let {
                item {
                    Text(text = it, modifier = Modifier.padding(horizontal = 16.dp))
                }
            } ?: item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Lugares Populares",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    HomePopularFilter(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        selectedRegion = state.selectedRegion,
                        selectRegion = {
                            viewModel.onRegionSelect(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(state.popularPlaces) { place ->
                            HomePopularPlaceItem(
                                place = place,
                                onPlaceClick = {
                                    viewModel.onSearchTextChange(it)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
