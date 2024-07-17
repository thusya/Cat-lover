package com.thusee.feature_cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thusee.core_data.async.AsyncOperation
import com.thusee.core_data.async.CatRepoAsyncEvents
import com.thusee.core_data.model.Cat
import com.thusee.core_data.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _catsStream = catsRepository.cats
    private val _catOperationStatus = catsRepository.operationStatus

    val uiState: StateFlow<CatsUIState> = getCombinedStream()

    init {
        fetchCats()
    }

    private fun getCombinedStream(): StateFlow<CatsUIState> = combine(
        _catsStream,
        _catOperationStatus,
    ) { cats, catOperationStatus ->
        createStatus(cats, catOperationStatus)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CatsUIState(isLoading = true)
    )

    private fun createStatus(
        cats: List<Cat>,
        catOperationStatus: AsyncOperation<CatRepoAsyncEvents>,
    ): CatsUIState {
        val newUserMessage = when (catOperationStatus) {
            is AsyncOperation.Loading -> null
            is AsyncOperation.Failure -> getUserMessage(catOperationStatus.asyncOp)
            is AsyncOperation.Success -> getUserMessage(catOperationStatus.asyncOp)
        }
        return CatsUIState(
            items = cats,
            isLoading = catOperationStatus is AsyncOperation.Loading,
            userMessage = newUserMessage,
        )
    }

    private fun getUserMessage(asyncOp: CatRepoAsyncEvents): Int {
        return when (asyncOp) {
            CatRepoAsyncEvents.FavoriteCatsFailed -> R.string.action_error
            CatRepoAsyncEvents.FavoriteCatsSuccess -> R.string.favorite_added
            CatRepoAsyncEvents.LoadCatsFailed -> R.string.action_error
            CatRepoAsyncEvents.LoadCatsSuccess -> R.string.cats_loaded
            CatRepoAsyncEvents.UnFavoriteCatsFailed -> R.string.favorite_removed
            is CatRepoAsyncEvents.UnFavoriteCatsSuccess -> R.string.favorite_removed
        }
    }

    fun fetchCats() = viewModelScope.launch { catsRepository.fetchCats() }

    fun onFavorite(cat: Cat) = viewModelScope.launch { catsRepository.onFavoriteCat(cat) }
}