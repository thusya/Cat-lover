package com.thusee.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_entity")
data class CatEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val description: String?,
    val isFavorite: Boolean?,
    val origin: String?,
    val lifeSpan: String?,
    val temperament: String?,
    val wikipediaUrl: String?,
)