package com.casm.socialnetwork.feature_post.presentation.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.models.Comment
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.presentation.components.ActionRow
import com.casm.socialnetwork.core.presentation.components.StandardToolBar
import com.casm.socialnetwork.core.presentation.ui.theme.MediumGray
import com.casm.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeMedium
import com.casm.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceLarge
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceSmall
import com.casm.socialnetwork.core.presentation.ui.theme.TextWhite

@Composable
fun PostDetailScreen(
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Spacer(modifier = Modifier.height(SpaceLarge))
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = ProfilePictureSizeMedium / 2f)
                                .clip(MaterialTheme.shapes.medium)
                                .shadow(5.dp)
                                .background(MediumGray)
                        ) {
                            state.post?.let { post ->
                                Image(
                                    rememberImagePainter(
                                        data = post.imageUrl,
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentDescription = stringResource(id = R.string.post_image),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(SpaceLarge)
                                ) {
                                    ActionRow(
                                        username = "Casm",
                                        modifier = Modifier.fillMaxWidth(),
                                        onLikeClick = {},
                                        onCommentClick = {},
                                        onShareClick = {},
                                        onUsernameClick = {}
                                    )
                                    Spacer(modifier = Modifier.height(SpaceSmall))
                                    Text(
                                        text = post.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextWhite,
                                    )
                                    Spacer(modifier = Modifier.height(SpaceMedium))
                                    Text(
                                        text = stringResource(
                                            id = R.string.liked_by_x_people,
                                            post.likeCount
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }

                        }
                        Image(
                            rememberImagePainter(
                                data = state.post?.profilePictureUrl,
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(ProfilePictureSizeMedium)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)
                        )

                        if (state.isLoadingPost) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                    }

                }
                Spacer(modifier = Modifier.height(SpaceLarge))
            }

            items(state.comments) { comment ->
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SpaceLarge, vertical = SpaceSmall),
                    comment = comment
                )
            }
        }
    }


}

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment,
    onLikedClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 5.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = comment.profilePictureUrl,
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(ProfilePictureSizeSmall)
                    )
                    Spacer(modifier = Modifier.width(SpaceSmall))
                    Text(
                        text = comment.username,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    text = comment.formattedTime,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(SpaceSmall))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(9f)
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                IconButton(
                    onClick = {
                        onLikedClick(comment.isLiked)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        tint = if (comment.isLiked) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        },
                        contentDescription = if (comment.isLiked) {
                            stringResource(id = R.string.unlike)
                        } else stringResource(id = R.string.like)
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceSmall))
            Text(
                text = stringResource(id = R.string.liked_by_x_people, comment.likeCount),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

