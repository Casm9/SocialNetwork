package com.casm.socialnetwork.feature_chat.presentation.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.presentation.components.SendTextField
import com.casm.socialnetwork.core.presentation.components.StandardToolBar
import com.casm.socialnetwork.core.presentation.ui.theme.DarkerGreen
import com.casm.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import com.casm.socialnetwork.feature_chat.domain.model.Message
import com.casm.socialnetwork.feature_chat.presentation.message.components.OwnMessage
import com.casm.socialnetwork.feature_chat.presentation.message.components.RemoteMessage

@Composable
fun MessageScreen(
    imageLoader: ImageLoader,
    chatId: String,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel()
) {
    val messages = remember {
        listOf(
            Message(
                fromId = "",
                toId = "",
                text = "Hello World",
                formattedTime = "18:54",
                chatId = "",
            ),
            Message(
                fromId = "",
                toId = "",
                text = "Hello World",
                formattedTime = "18:54",
                chatId = "",
            ),
            Message(
                fromId = "",
                toId = "",
                text = "Hello World",
                formattedTime = "18:54",
                chatId = "",
            ),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StandardToolBar(
            showBackArrow = true,
            onNavigateUp = onNavigateUp,
            title = {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "http://10.0.2.2:8001/profile_pictures/avatar.svg",
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
                Text(
                    text = "Casm",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(SpaceMedium)
            ) {
                items(messages) { message ->
                    RemoteMessage(
                        message = message.text,
                        formattedTime = message.formattedTime,
                        color = MaterialTheme.colorScheme.surface,
                        textColor = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    OwnMessage(
                        message = message.text,
                        formattedTime = message.formattedTime,
                        color = DarkerGreen,
                        textColor = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }

            }
            SendTextField(
                state = viewModel.messageTextFieldState.value,
                onValueChange = {
                    viewModel.onEvent(MessageEvent.EnteredMessage(it))
                },
                hint = stringResource(id = R.string.enter_a_message),
                onSend = {
                    viewModel.onEvent(MessageEvent.SendMessage)
                }
            )
        }
    }

}