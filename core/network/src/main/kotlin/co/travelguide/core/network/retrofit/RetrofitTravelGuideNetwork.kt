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

package co.travelguide.core.network.retrofit

import androidx.tracing.trace
import co.travelguide.core.network.TravelGuideNetworkDataSource
import co.travelguide.core.network.model.NetworkPlace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for NIA Network API
 */
private interface RetrofitTravelGuideNetworkApi {
    @GET("places.json")
    suspend fun getPlaces(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkPlace>>
}

private const val PLACES_URL = BuildConfig.PLACES_URL

/**
 * Wrapper for data provided from the [PLACES_URL]
 */
@Serializable
private data class NetworkResponse<T>(
    val data: T,
)

/**
 * [Retrofit] backed [TravelGuideNetworkDataSource]
 */
@Singleton
internal class RetrofitTravelGuideNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : TravelGuideNetworkDataSource {

    private val networkApi = trace("RetrofitTravelGuideNetwork") {
        Retrofit.Builder()
            .baseUrl(PLACES_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitTravelGuideNetworkApi::class.java)
    }

    override suspend fun getPlaces(ids: List<String>?): List<NetworkPlace> =
        networkApi.getPlaces(ids = ids).data
}