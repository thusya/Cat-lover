package com.thusee.feature_cats.catlist

import com.thusee.core_data.model.Cat

data class CatsUIState(
    val items: List<Cat> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)