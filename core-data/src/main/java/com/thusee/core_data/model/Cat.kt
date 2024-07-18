package com.thusee.core_data.model

data class Cat(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val isFavorite: Boolean = false,
    val origin: String = "",
    val lifeSpan: String = "",
    val temperament: List<String> = emptyList(),
    val wikipediaUrl: String = "",
)