package com.thusee.feature_cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thusee.core_data.model.Cat
import com.thusee.core_data.repository.CatsRepository
import com.thusee.core_data.utils.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val catsStream = catsRepository.cats

    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<CatsUIState> = combine(
        catsStream, _userMessage, _isLoading
    ) { cats, userMessage, isLoading ->
        if (isLoading) {
            CatsUIState(
                cats,
                isLoading = true,
            )
        } else {
            CatsUIState(
                cats,
                isLoading = false,
                userMessage
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CatsUIState(isLoading = true)
    )

    suspend fun fetchCats() {
        _isLoading.value = true

        val result = catsRepository.fetchCats()

        if (result is ResultOf.Failure) {
            showSnakbarMessage(R.string.network_error)
        } else {
            _isLoading.value = false
        }
    }

    private fun showSnakbarMessage(message: Int) {
        _userMessage.value
    }

    fun onFavorite(cat: Cat) = viewModelScope.launch {
        _isLoading.value = true
        val result = catsRepository.onFavoriteCat(cat)
        _isLoading.value = false

        if (result is ResultOf.Success) {
            if (!cat.isFavorite) {
                showSnakbarMessage(R.string.favorite_added)
            } else {
                showSnakbarMessage(R.string.favorite_removed)
            }
        } else {
            showSnakbarMessage(R.string.action_error)
        }
    }
}