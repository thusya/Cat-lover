package com.thusee.core_data.utils

import com.thusee.core_data.model.Cat
import com.thusee.core_database.model.CatEntity
import com.thusee.core_network.model.CatResponse

fun CatEntity.toCat() = Cat(
    id = id,
    breed = breed,
    image = image,
    description = description ?: "",
    isFavorite = isFavorite ?: false,
)

fun CatResponse.toCatEntity() = CatEntity(
    id = id,
    breed = breeds[0].name,
    image = url,
    description = breeds[0].description,
    isFavorite = false,
)

fun Cat.toCatEntity() = CatEntity(
    id = id,
    breed = breed,
    image = image,
    description = description,
    isFavorite = isFavorite,
)