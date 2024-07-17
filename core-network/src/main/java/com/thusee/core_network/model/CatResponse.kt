package com.thusee.core_network.model

import com.thusee.core_network.model.Breed
import kotlinx.serialization.Serializable

@Serializable
data class CatResponse(
    val breeds: List<Breed>,
    val id: String,
    val url: String,
)