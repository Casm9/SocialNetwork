package com.casm.socialnetwork.feature_chat.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceLarge
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import com.casm.socialnetwork.core.util.Screen
import com.casm.socialnetwork.feature_chat.domain.model.Chat

@Composable
fun ChatScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    imageLoader: ImageLoader
) {
    val chats = remember {
        listOf(
            Chat(
                remoteUserUsername = "Casm",
                remoteUserProfileUrl = "http://10.0.2.2:8001/profile_pictures/avatar.svg",
                lastMessage = "This is last message test",
                lastMessageFormattedTime = "14:16"
            ),
            Chat(
                remoteUserUsername = "Casm",
                remoteUserProfileUrl = "http://10.0.2.2:8001/profile_pictures/avatar.svg",
                lastMessage = "This is last message test",
                lastMessageFormattedTime = "14:16"
            ),
            Chat(
                remoteUserUsername = "Casm",
                remoteUserProfileUrl = "http://10.0.2.2:8001/profile_pictures/avatar.svg",
                lastMessage = "This is last message test",
                lastMessageFormattedTime = "14:16"
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(SpaceMedium),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(chats) { chat ->
                ChatItem(
                    item = chat,
                    imageLoader = imageLoader,
                    onItemClick = {
                        onNavigate(Screen.MessagesScreen.route)
                    }
                )
                Spacer(modifier = Modifier.height(SpaceLarge))
            }
        }
    }
}