package com.example.apiboutiqueexame.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val password: String,
    val name: String = "",
    val biography: String = "",
    val profileImageUrl: String? = null,
    val logged: Boolean = false
)

