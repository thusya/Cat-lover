package com.thusee.core_data.utils

import com.thusee.core_data.model.Cat
import com.thusee.core_database.model.CatEntity
import com.thusee.core_network.model.Breed
import com.thusee.core_network.model.CatResponse
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ModelConversionTests {
    @Test
    fun testCatEntityToCatConversion() {
        val catEntity = CatEntity(
            id = "cat1",
            name = "Siamese",
            imageUrl = "url",
            description = "A cat",
            isFavorite = true,
            origin = "Thailand",
            lifeSpan = "10-12",
            temperament = "Affectionate, Intelligent, Agile",
            wikipediaUrl = "https://en.wikipedia.org/wiki/Siamese_cat"
        )

        val cat = catEntity.toCat()

        assertEquals("cat1", cat.id)
        assertEquals("Siamese", cat.name)
        assertEquals("url", cat.imageUrl)
        assertEquals("A cat", cat.description)
        assertEquals(true, cat.isFavorite)
        assertEquals("Thailand", cat.origin)
        assertEquals("10-12", cat.lifeSpan)
        assertEquals(listOf("Affectionate", "Intelligent", "Agile"), cat.temperament)
        assertEquals("https://en.wikipedia.org/wiki/Siamese_cat", cat.wikipediaUrl)
    }

    @Test
    fun testCatResponseToCatEntityConversion() {
        val breed = Breed(
            id = "be",
            name = "Siamese",
            description = "Elegant type of cat",
            wikipedia_url = "https://en.wikipedia.org/wiki/Siamese_cat",
            temperament = "Playful, Sociable",
            origin = "Thailand",
            life_span = "10-15"
        )
        val catResponse = CatResponse(
            id = "cat2",
            url = "url",
            breeds = listOf(breed)
        )

        val catEntity = catResponse.toCatEntity()

        assertEquals("cat2", catEntity.id)
        assertEquals("Siamese", catEntity.name)
        assertEquals("url", catEntity.imageUrl)
        assertEquals("Elegant type of cat", catEntity.description)
        assertEquals(false, catEntity.isFavorite)
        assertEquals("Thailand", catEntity.origin)
        assertEquals("10-15", catEntity.lifeSpan)
        assertEquals("Playful, Sociable", catEntity.temperament)
        assertEquals("https://en.wikipedia.org/wiki/Siamese_cat", catEntity.wikipediaUrl)
    }

    @Test
    fun testCatToCatEntityConversion() {
        val cat = Cat(
            id = "cat3",
            name = "Siamese",
            imageUrl = "url",
            description = "A cat",
            isFavorite = false,
            origin = "Thailand",
            lifeSpan = "10-12",
            temperament = listOf("Affectionate", "Intelligent", "Agile"),
            wikipediaUrl = "https://en.wikipedia.org/wiki/Siamese_cat"
        )

        val catEntity = cat.toCatEntity()

        assertEquals("cat3", catEntity.id)
        assertEquals("Siamese", catEntity.name)
        assertEquals("url", catEntity.imageUrl)
        assertEquals("A cat", catEntity.description)
        assertEquals(false, catEntity.isFavorite)
        assertEquals("Thailand", catEntity.origin)
        assertEquals("10-12 years", catEntity.lifeSpan)
        assertEquals("Affectionate, Intelligent, Agile", catEntity.temperament)
        assertEquals("https://en.wikipedia.org/wiki/Siamese_cat", catEntity.wikipediaUrl)
    }
}