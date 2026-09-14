package com.example.myapplication.data.network.dto

import com.example.myapplication.data.local.MessageEntity
import com.example.myapplication.domain.Message

import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

fun MessageDto.toDomain(): Message = Message(
    id = id ?: "", // GIVEN — copy this pattern
    // TODO 3a: sender — use the sender, but if it is null use "Unknown" instead
    sender = sender ?: "Unknown",
    // TODO 3b: text — use the text, but if it is null use an empty string ""
    text = text ?: "",
    // TODO 3c: createdAt — use createdAt, but if it is null use 0L
    // NOTE: createdAt is now a JsonElement, so we try to get it as a Long.
    createdAt = createdAt?.jsonPrimitive?.longOrNull ?: 0L
)

fun List<MessageDto>.toDomain(): List<Message> = // GIVEN (read it, do not change it)
    map { it.toDomain() }

fun Message.toEntity(): MessageEntity = MessageEntity(
    id = id,
    sender = sender,
    text = text,
    createdAt = createdAt
)

fun MessageEntity.toDomain(): Message = Message(
    id = id,
    sender = sender,
    text = text,
    createdAt = createdAt
)

fun List<MessageEntity>.toDomainFromEntity(): List<Message> =
    map { it.toDomain() }
