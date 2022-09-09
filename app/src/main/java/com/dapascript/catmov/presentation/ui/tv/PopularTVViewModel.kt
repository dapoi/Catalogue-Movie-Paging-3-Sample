package com.dapascript.catmov.presentation.ui.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dapascript.catmov.data.TVRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularTVViewModel @Inject constructor(
    private val repositoryImpl: TVRepositoryImpl
) : ViewModel() {
    fun getPopularTV() = repositoryImpl.getPopularTVShow().cachedIn(viewModelScope)
}