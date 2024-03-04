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

package co.travelguide.core.network.fake

import JvmUnitTestFakeAssetManager
import co.travelguide.core.network.Dispatcher
import co.travelguide.core.network.TravelGuideDispatchers
import co.travelguide.core.network.TravelGuideNetworkDataSource
import co.travelguide.core.network.model.NetworkPlace
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

/**
 * [TravelGuideNetworkDataSource] implementation that provides static news resources to aid development
 */
class FakeTravelGuideNetworkDataSource @Inject constructor(
    @Dispatcher(TravelGuideDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : TravelGuideNetworkDataSource {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getPlaces(ids: List<String>?): List<NetworkPlace> =
        withContext(ioDispatcher) {
            assets.open(PLACES_ASSET).use(networkJson::decodeFromStream)
        }

    companion object {
        private const val PLACES_ASSET = "places.json"
    }
}
