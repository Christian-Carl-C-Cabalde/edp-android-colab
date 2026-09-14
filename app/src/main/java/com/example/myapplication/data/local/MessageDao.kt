package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    // TODO 13c: a @Query that selects everything from messages,
    // ORDER BY createdAt DESC, returning List<MessageEntity>
    // (make it a suspend function)
    @Query("SELECT * FROM messages ORDER BY createdAt DESC")
    suspend fun getAll(): List<MessageEntity>

    // TODO 13d: an @Insert with onConflict = OnConflictStrategy.REPLACE
    // taking a List<MessageEntity>, suspend, returning nothing
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MessageEntity>)
}
