package com.casm.socialnetwork.core.presentation.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
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
import com.casm.socialnetwork.core.domain.models.UserItem
import com.casm.socialnetwork.core.presentation.ui.theme.IconSizeMedium
import com.casm.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceSmall

@Composable
fun UserProfileItem(
    user: UserItem,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    actionIcon: @Composable () -> Unit = {},
    onItemClick: () -> Unit = {},
    onActionItemClick: () -> Unit = {},
    ownUserId: String = ""
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onItemClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
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
                    model = user.profilePictureUrl,
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
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = user.bio,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.heightIn(
                        min = (MaterialTheme.typography.bodyMedium.fontSize.value.dp * 2.5f)
                    )
                )
            }
            if (user.userId != ownUserId) {
                IconButton(
                    onClick = onActionItemClick,
                    modifier = Modifier.size(IconSizeMedium)
                ) {
                    actionIcon()
                }
            }
        }
    }
}