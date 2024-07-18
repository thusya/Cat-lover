package com.thusee.feature_cats.catlist

import com.thusee.core_data.model.Cat

sealed class CatsUIState {
    data class Data(val items: List<Cat>) : CatsUIState()
    data object Loading : CatsUIState()
    data class Message(val messageId: Int) : CatsUIState()
}