package com.thusee.core_data.utils

import com.thusee.core_data.model.Cat
import com.thusee.core_database.model.CatEntity
import com.thusee.core_network.model.CatResponse

fun CatEntity.toCat() = Cat(
    id = id,
    name = name,
    imageUrl = imageUrl,
    description = description ?: "",
    isFavorite = isFavorite ?: false,
    origin = origin ?: "",
    lifeSpan = lifeSpan ?: "",
    temperament = temperament?.split(",") ?: emptyList(),
    wikipediaUrl = wikipediaUrl ?: ""
)

fun CatResponse.toCatEntity() = CatEntity(
    id = id,
    name = breeds.firstOrNull()?.name ?: "",
    imageUrl = url,
    description = breeds.firstOrNull()?.description,
    isFavorite = false,
    origin = breeds.firstOrNull()?.origin,
    lifeSpan = breeds.firstOrNull()?.life_span,
    temperament = breeds.firstOrNull()?.temperament,
    wikipediaUrl = breeds.firstOrNull()?.wikipedia_url
)

fun Cat.toCatEntity() = CatEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    description = description,
    isFavorite = isFavorite,
    origin = origin,
    lifeSpan = lifeSpan,
    temperament = temperament.joinToString(","),
    wikipediaUrl = wikipediaUrl
)