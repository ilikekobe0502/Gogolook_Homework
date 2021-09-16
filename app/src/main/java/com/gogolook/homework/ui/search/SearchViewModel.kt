package com.gogolook.homework.ui.search

import android.util.ArraySet
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gogolook.homework.enums.DisplayMode
import com.gogolook.homework.model.api.ApiResult
import com.gogolook.homework.model.api.model.response.ImageInfo
import com.gogolook.homework.model.repository.GogolookRepository
import com.gogolook.homework.ui.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel @ViewModelInject constructor(
    private var gogolookRepository: GogolookRepository
) : BaseViewModel() {

    private val _imagesResult = MutableLiveData<ApiResult<List<ImageInfo>?>>() // Images liveData
    val imagesResult: LiveData<ApiResult<List<ImageInfo>?>> = _imagesResult

    private val _searchHistoryResult = MutableLiveData<List<String>>() // Images liveData
    val searchHistoryResult: LiveData<List<String>> = _searchHistoryResult

    private val _displayModeResult = MutableLiveData<DisplayMode>() // Images liveData
    val displayModeResult: LiveData<DisplayMode> = _displayModeResult

    private var imageList : ArraySet<ImageInfo> = ArraySet()
    private var searchHistoryList : ArraySet<String> = ArraySet()
    var page = 0

    /**
     * Get images from api
     *
     * @param keyword the text user want to search
     */
    fun getImages(keyword: String, reset: Boolean = false) {
        if (reset) {
            imageList.clear()
            page = 1
        } else {
            page++
        }

        if(keyword.isNotEmpty()){
            searchHistoryList.add(keyword)
            _searchHistoryResult.postValue(searchHistoryList.toList())
        }

        viewModelScope.launch {
            gogolookRepository.getImages(keyword, page.toString())
                .onStart { _imagesResult.value = ApiResult.loading() }
                .catch { e -> _imagesResult.value = ApiResult.error(e) }
                .onCompletion {
                    _imagesResult.value = ApiResult.loaded()
                }
                .collect {
                    imageList.addAll(it)
                    _imagesResult.value = ApiResult.success(imageList.toList())
                }
        }
    }

    /**
     * get display mode from remote
     */
    fun getDisplayMode() {
        viewModelScope.launch {
            gogolookRepository.getDisplayMode()
                .onStart { Timber.d("Mode api start to load") }
                .catch { e ->
                    Timber.d(" Mode api failed = $e")
                    _displayModeResult.value = DisplayMode.GRID
                }
                .onCompletion { Timber.d("Mode api complete") }
                .collect { _displayModeResult.value = it }
        }
    }

    /**
     * change the display mode
     */
    fun changeDisplayMode() {
        when (displayModeResult.value) {
            DisplayMode.LIST -> {
                _displayModeResult.postValue(DisplayMode.GRID)
            }
            DisplayMode.GRID -> {
                _displayModeResult.postValue(DisplayMode.LIST)
            }
        }
    }
}