package com.example.myapplication.data.repository

import com.example.myapplication.core.AppResult
import com.example.myapplication.data.local.MessageDao
import com.example.myapplication.data.network.ChatApiService
import com.example.myapplication.data.network.dto.NewMessageDto
import com.example.myapplication.data.network.dto.toDomain
import com.example.myapplication.data.network.dto.toEntity
import com.example.myapplication.domain.ChatRepository
import com.example.myapplication.domain.Message
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChatRepositoryImpl(
    private val api: ChatApiService,
    private val dao: MessageDao
) : ChatRepository {

    override suspend fun getMessages(): AppResult<List<Message>> {
        // TODO 14a: try the network first, exactly as in TODO 7.
        val result = safeCall { api.getMessages().toDomain() }
        
        // If Success, save them into Room with dao.insertAll(...)
        // and then return that Success.
        if (result is AppResult.Success) {
            try {
                dao.insertAll(result.data.map { it.toEntity() })
            } catch (e: Exception) {
                // If saving fails, we still return the success from network
            }
            return result
        }

        // TODO 14b: if it is a Failure, read dao.getAll() instead.
        val saved = try {
            dao.getAll().map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
        
        // If the saved list is NOT empty, return AppResult.Success with it.
        // If it IS empty, return the original Failure.
        return if (saved.isNotEmpty()) {
            AppResult.Success(saved)
        } else {
            result
        }
    }

    override suspend fun sendMessage(sender: String, text: String): AppResult<Unit> =
        safeCall {
            val dto = NewMessageDto(sender, text, System.currentTimeMillis())
            api.sendMessage(dto)
            Unit
        }

    private inline fun <T> safeCall(block: () -> T): AppResult<T> =
        try { AppResult.Success(block()) }
        catch (e: UnknownHostException) { AppResult.Failure.NoInternet }
        catch (e: SocketTimeoutException) { AppResult.Failure.Timeout }
        catch (e: IOException) { AppResult.Failure.NoInternet }
        catch (e: Exception) { AppResult.Failure.Unknown(e.message) }
}
