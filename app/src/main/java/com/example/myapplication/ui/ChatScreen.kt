package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.domain.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(factory = ChatViewModel.Factory)
) {
    Scaffold( // GIVEN
        topBar = {
            TopAppBar(title = { Text("LiceoChat") }, actions = {
                IconButton(onClick = { viewModel.load() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            })
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()) {
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()) {
                // TODO 12: draw the four states here.
                when (val state = viewModel.uiState) {
                    ChatUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    ChatUiState.Empty -> Text(
                        text = "No messages yet. Say hello!",
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is ChatUiState.Ready -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        reverseLayout = true
                    ) {
                        items(state.messages, key = { it.id }) {
                            val isMine = it.sender == viewModel.myName
                            MessageRow(it, isMine)
                        }
                    }
                    is ChatUiState.Error -> Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.message)
                        Button(onClick = { viewModel.load() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            MessageInput( // GIVEN — name box, text box, Send button
                name = viewModel.myName,
                draft = viewModel.draft,
                onNameChange = viewModel::onNameChange,
                onDraftChange = viewModel::onDraftChange,
                onSend = viewModel::send
            )
        }
    }
}

@Composable
fun MessageRow(message: Message, isMine: Boolean) {
    val alignment = if (isMine) Alignment.CenterEnd else Alignment.CenterStart
    val bubbleColor = if (isMine) Color(0xFF007AFF) else Color(0xFFE9E9EB)
    val textColor = if (isMine) Color.White else Color.Black
    
    // Custom shape: slightly sharper corner on the "tail" side
    val shape = if (isMine) {
        RoundedCornerShape(18.dp, 18.dp, 2.dp, 18.dp)
    } else {
        RoundedCornerShape(18.dp, 18.dp, 18.dp, 2.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        contentAlignment = alignment
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            if (!isMine) {
                UserAvatar(message.sender)
                Spacer(Modifier.width(8.dp))
            }
            
            Surface(
                color = bubbleColor,
                shape = shape,
                modifier = Modifier.widthIn(max = 250.dp)
            ) {
                Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    if (!isMine) {
                        Text(
                            text = message.sender,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun UserAvatar(name: String) {
    val initials = name.split(" ")
        .filter { it.isNotEmpty() }
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .take(2)
        .joinToString("")

    // Deterministic color based on name hash
    val colors = listOf(
        Color(0xFFE57373), Color(0xFFF06292), Color(0xFFBA68C8), Color(0xFF9575CD),
        Color(0xFF7986CB), Color(0xFF64B5F6), Color(0xFF4FC3F7), Color(0xFF4DB6AC),
        Color(0xFF81C784), Color(0xFFAED581), Color(0xFFFFD54F), Color(0xFFFFB74D)
    )
    val backgroundColor = colors[name.hashCode().coerceAtLeast(0) % colors.size]

    Surface(
        modifier = Modifier.size(36.dp),
        shape = RoundedCornerShape(18.dp),
        color = backgroundColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = initials,
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
        }
    }
}

@Composable // GIVEN (read it, do not change it)
fun MessageInput(
    name: String,
    draft: String,
    onNameChange: (String) -> Unit,
    onDraftChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Your full name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = draft,
                onValueChange = onDraftChange,
                label = { Text("Message") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = onSend) { Text("Send") }
        }
    }
}
