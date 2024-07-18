package com.thusee.core_network.model

import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val id: String,
    val name: String,
    val description: String,
//    val wikipedia_url: String,
//    val temperament: String,
//    val origin: String,
//    val life_span: String,
)
//{
//    val temperamentList: List<String>
//        get() = temperament.split(", ").filter { it.isNotBlank() }
//}