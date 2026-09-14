package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    // TODO 13a: id as the @PrimaryKey, type String
    @PrimaryKey val id: String,
    // TODO 13b: sender, text (String) and createdAt (Long)
    val sender: String,
    val text: String,
    val createdAt: Long
)
