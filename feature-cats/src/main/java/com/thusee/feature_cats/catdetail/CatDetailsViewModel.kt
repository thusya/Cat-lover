package com.thusee.feature_cats.catdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thusee.core_data.model.Cat
import com.thusee.core_data.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CatDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CatsRepository
) : ViewModel() {

    private val catId: String =
        savedStateHandle.get<String>("catId") ?: ""

    val catDetails: StateFlow<Cat> = repository.getCatById(catId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Cat()
    )
}