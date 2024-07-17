package com.thusee.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_entity")
data class CatEntity(
    @PrimaryKey val id: String,
    val breed: String,
    val image: String,
    val description: String?,
    val isFavorite: Boolean?,
)