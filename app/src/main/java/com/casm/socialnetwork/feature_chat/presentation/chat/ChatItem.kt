package com.casm.socialnetwork.feature_chat.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceSmall
import com.casm.socialnetwork.feature_chat.domain.model.Chat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatItem(
    item: Chat,
    imageLoader: ImageLoader,
    onItemClick: (Chat) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = { onItemClick(item) },
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(vertical = SpaceSmall, horizontal = SpaceMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = item.remoteUserProfileUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = stringResource(id = R.string.profile_image),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(ProfilePictureSizeSmall)
            )
            Spacer(modifier = Modifier.width(SpaceSmall))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = item.remoteUserUsername,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(SpaceSmall))
                    Text(text = item.lastMessageFormattedTime)
                }

                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = item.lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.heightIn(
                        min = (MaterialTheme.typography.bodyMedium.fontSize.value.dp * 3f)
                    )
                )
            }
        }
    }
}