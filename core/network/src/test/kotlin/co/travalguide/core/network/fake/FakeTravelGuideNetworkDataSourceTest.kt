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

package co.travalguide.core.network.fake

import JvmUnitTestFakeAssetManager
import co.travelguide.core.network.fake.FakeTravelGuideNetworkDataSource
import co.travelguide.core.network.model.NetworkPlace
import co.travelguide.core.network.model.Region
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FakeTravelGuideNetworkDataSourceTest {

    private lateinit var subject: FakeTravelGuideNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        subject = FakeTravelGuideNetworkDataSource(
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Suppress("ktlint:standard:max-line-length")
    @Test
    fun testDeserializationOfPlaces() = runTest(testDispatcher) {
        assertEquals(
            NetworkPlace(
                id = 1,
                country = "Country 1",
                city = "City 1",
                region = "Region 1",
                image = "https://firebasestorage.googleapis.com/v0/b/now-in-android.appspot.com/o/img%2Fic_topic_Headlines.svg?alt=media&token=506faab0-617a-4668-9e63-4a2fb996603f"
            ),
            subject.getPlaces().first(),
        )
    }

}
