package com.gogolook.homework.model.repository

import com.gogolook.homework.API_KEY
import com.gogolook.homework.enums.DisplayMode
import com.gogolook.homework.model.api.ApiService
import com.gogolook.homework.model.api.model.response.ImageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

class GogolookRepository constructor(private val apiService: ApiService) {

    suspend fun getImages(s: String = "", page: String): Flow<ArrayList<ImageInfo>> {
        val queryMap = HashMap<String, String>()
        queryMap["key"] = API_KEY
        queryMap["q"] = s
        queryMap["page"] = page

        return flowOf(apiService.getImages(queryMap))
            .map { result ->
                if (!result.isSuccessful) throw HttpException(result)
                return@map result.body()?.hits ?: throw Exception("Data is empty")
            }.flowOn(Dispatchers.IO)
    }

    /**
     * use it for pretend there has the display mode api
     */
    suspend fun getDisplayMode(): Flow<DisplayMode> {
        return flowOf(DisplayMode.GRID)
            .flowOn(Dispatchers.IO)
    }
}