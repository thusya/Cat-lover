package com.thusee.core_data.fakes

import com.thusee.core_network.model.Breed
import com.thusee.core_network.model.CatResponse

val fakeCatResponse = listOf(
    CatResponse(
        listOf(
            Breed(
                id = "be",
                name = "Siamese",
                description = "Elegant type of cat",
                wikipedia_url = "https://en.wikipedia.org/wiki/Siamese_cat",
                temperament = "Playful, Sociable",
                origin = "Thailand",
                life_span = "10-15"
            )
        ),
        "askldn",
        "url1"
    ),
    CatResponse(
        listOf(
            Breed(
                id = "be1",
                name = "Siamese",
                description = "Elegant type of cat",
                wikipedia_url = "https://en.wikipedia.org/wiki/Siamese_cat",
                temperament = "Playful, Sociable",
                origin = "Thailand",
                life_span = "10-15"
            )
        ),
        "polmk",
        "url1"
    ),
    CatResponse(
        listOf(
            Breed(
                id = "be2",
                name = "Siamese",
                description = "Elegant type of cat",
                wikipedia_url = "https://en.wikipedia.org/wiki/Siamese_cat",
                temperament = "Playful, Sociable",
                origin = "Thailand",
                life_span = "10-15"
            )
        ),
        "nxhu",
        "url1"
    )
)