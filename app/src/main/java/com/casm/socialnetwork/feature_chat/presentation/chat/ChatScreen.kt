package com.casm.socialnetwork.feature_chat.presentation.chat

import android.util.Base64
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceLarge
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import com.casm.socialnetwork.core.util.Screen

@Composable
fun ChatScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    imageLoader: ImageLoader,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val chats = viewModel.state.value.chats
    val isLoading = viewModel.state.value.isLoading
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
                        onNavigate(Screen.MessagesScreen.route + "/${chat.remoteUserId}/${chat.remoteUsername}/${Base64.encodeToString(chat.remoteUserProfilePictureUrl.encodeToByteArray(), 0)}?chatId=${chat.chatId}")
                    }
                )
                Spacer(modifier = Modifier.height(SpaceLarge))
            }
        }
    }
}