package com.casm.socialnetwork.feature_post.presentation.main_feed


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.paging.compose.items
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.presentation.components.Post
import com.casm.socialnetwork.core.presentation.components.StandardScaffold
import com.casm.socialnetwork.core.presentation.components.StandardToolBar
import com.casm.socialnetwork.core.util.Screen
import kotlinx.coroutines.launch

@Composable
fun MainFeedScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: MainFeedViewModel = hiltViewModel()
) {
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
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
            showBackArrow = false,
            navActions = {
                IconButton(onClick = {
                    onNavigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            }

        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoadingFirstTime) {
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
            LazyColumn {
                items(posts) { post ->
                    Post(
                        post = Post(
                            id = post?.id ?: "",
                            username = post?.username ?: "",
                            imageUrl = post?.imageUrl ?: "",
                            profilePictureUrl = post?.profilePictureUrl ?: "",
                            description = post?.description ?: "",
                            likeCount = post?.likeCount ?: 0,
                            commentCount = post?.commentCount ?: 0
                        ),
                        onPostClick = {
                            onNavigate(Screen.PostDetailScreen.route + "${post?.id}")
                        }
                    )
                }
                item {
                    if(state.isLoadingNewPosts) {
                        CircularProgressIndicator(modifier = Modifier.align(BottomCenter))
                    }
                }
                posts.apply {
                    when {
                        loadState.refresh !is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadMorePosts)
                        }
                        loadState.append is LoadState.NotLoading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Error -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Error"
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}