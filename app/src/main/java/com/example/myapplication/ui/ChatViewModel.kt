package com.example.myapplication.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.core.AppResult
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.network.NetworkModule
import com.example.myapplication.data.repository.ChatRepositoryImpl
import com.example.myapplication.domain.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository // GIVEN
) : ViewModel() {
    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading) // GIVEN
        private set
    var myName: String by mutableStateOf("") // GIVEN — the name typed in the app
        private set
    var draft: String by mutableStateOf("") // GIVEN — the message being typed
        private set

    fun onNameChange(value: String) {
        myName = value
    } // GIVEN

    fun onDraftChange(value: String) {
        draft = value
    } // GIVEN

    init {
        load()
    } // GIVEN

    fun load() {
        viewModelScope.launch {
            uiState = ChatUiState.Loading
            uiState = when (val r = repository.getMessages()) {
                is AppResult.Success -> if (r.data.isEmpty()) ChatUiState.Empty else ChatUiState.Ready(r.data)
                AppResult.Failure.NoInternet -> ChatUiState.Error("No internet connection.")
                AppResult.Failure.Timeout -> ChatUiState.Error("The server took too long.")
                is AppResult.Failure -> ChatUiState.Error("Something went wrong.")
            }
        }
    }

    fun send() {
        if (myName.isBlank() || draft.isBlank()) return
        viewModelScope.launch {
            when (repository.sendMessage(myName, draft)) {
                is AppResult.Success -> {
                    draft = ""
                    load()
                }
                else -> uiState = ChatUiState.Error("Could not send. Check your connection.")
            }
        }
    }

    companion object { // GIVEN (read it, do not change it)
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                ChatViewModel(
                    ChatRepositoryImpl(
                        NetworkModule.chatApi,
                        AppDatabase.getDatabase(application).messageDao()
                    )
                )
            }
        }
    }
}
