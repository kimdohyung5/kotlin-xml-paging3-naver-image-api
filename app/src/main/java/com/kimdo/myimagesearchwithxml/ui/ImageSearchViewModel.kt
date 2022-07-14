package com.kimdo.myimagesearchwithxml.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kimdo.myimagesearchwithxml.data.NaverImageSearchRepository
import com.kimdo.myimagesearchwithxml.model.Item
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
class ImageSearchViewModel: ViewModel() {
    private val repository = NaverImageSearchRepository()
    private val queryFlow = MutableSharedFlow<String>()
    private val favorites = mutableSetOf<Item>()
    private val _favoritesFlow = MutableSharedFlow<List<Item>>(replay = 1)

    // pagingDataFlow, favoritesFlow로 외부에 알리고
    // handleQuery, toggle을 통해서 검색 또는 favorite를 삭제 관린한다.
    // handleQuery이면 문자열이 마뀌어서 다시 쿼리를 날리는 역황ㄹ을 한다.
    // favorites것은 특별히 하는 역활이 없다.
    // 쿼리값을 통해서 이미지를 searching해서 paingDataFlow에 데애터를 가짐..

    val pagingDataFlow = queryFlow
        .flatMapLatest {
            searchImages(it)
        }
        .cachedIn(viewModelScope)

    val favoritesFlow = _favoritesFlow.asSharedFlow()

    private fun searchImages(query: String): Flow<PagingData<Item>> =
        repository.getImageSearch(query)

    fun handleQuery(query: String) {
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }

    fun toggle(item: Item) {
        if(favorites.contains(item)) {
            favorites.remove(item)
        } else {
            favorites.add(item)
        }
        viewModelScope.launch {
            _favoritesFlow.emit(favorites.toList())
        }
    }

}