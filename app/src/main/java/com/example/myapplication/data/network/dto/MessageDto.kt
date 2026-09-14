package com.example.myapplication.data.network.dto

import kotlinx.serialization.Serializable

import kotlinx.serialization.json.JsonElement

@Serializable
data class MessageDto(
    // TODO 1a: add id. It arrives as a string in quotes.
    // Make it nullable with a default of null.
    val id: String? = null,
    // TODO 1b: add sender. Same idea — a nullable String with a default of null.
    val sender: String? = null,
    // TODO 1c: add text. A nullable String with a default of null.
    val text: String? = null,
    // TODO 1d: add createdAt. It is a big number of milliseconds,
    // so use Long, nullable, with a default of null.
    // NOTE: Changed to JsonElement to handle both Long and String timestamps from the server.
    val createdAt: JsonElement? = null
)

@Serializable
data class NewMessageDto( // GIVEN (read it, do not change it)
    val sender: String, // no id here — the server makes it
    val text: String,
    val createdAt: Long
)
