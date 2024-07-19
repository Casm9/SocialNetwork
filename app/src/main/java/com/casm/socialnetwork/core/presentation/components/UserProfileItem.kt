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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.models.User
import com.casm.socialnetwork.core.presentation.ui.theme.IconSizeMedium
import com.casm.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceSmall

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserProfileItem(
    user: User,
    actionIcon: @Composable () -> Unit = {},
    onItemClick: () -> Unit = {},
    onActionItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onItemClick,
        elevation = 5.dp,

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
                painter = rememberImagePainter(
                    data = user.profilePictureUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .clip(CircleShape)

            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = SpaceSmall)
            ) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = user.description,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            IconButton(
                onClick = onActionItemClick,
                modifier = Modifier.size(IconSizeMedium)
            ) {
                actionIcon()
            }

        }
    }
}