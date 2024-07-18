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
            breed = "Siamese",
            image = "url",
            description = "A cat",
            isFavorite = true
        )

        val cat = catEntity.toCat()

        assertEquals("cat1", cat.id)
        assertEquals("Siamese", cat.breed)
        assertEquals("url", cat.image)
        assertEquals("A cat", cat.description)
        assertEquals(true, cat.isFavorite)
    }

    @Test
    fun testCatResponseToCatEntityConversion() {
        val catResponse = CatResponse(
            id = "cat2",
            url = "url",
            breeds = listOf(Breed(id = "be", name = "Siamese", description = "cat"))
        )

        val catEntity = catResponse.toCatEntity()

        assertEquals("cat2", catEntity.id)
        assertEquals("Siamese", catEntity.breed)
        assertEquals("url", catEntity.image)
        assertEquals("cat", catEntity.description)
        assertEquals(false, catEntity.isFavorite)
    }

    @Test
    fun testCatToCatEntityConversion() {
        val cat = Cat(
            id = "cat3",
            breed = "Siamese",
            image = "url",
            description = "A cat",
            isFavorite = false
        )

        val catEntity = cat.toCatEntity()

        assertEquals("cat3", catEntity.id)
        assertEquals("Siamese", catEntity.breed)
        assertEquals("url", catEntity.image)
        assertEquals("A cat", catEntity.description)
        assertEquals(false, catEntity.isFavorite)
    }
}