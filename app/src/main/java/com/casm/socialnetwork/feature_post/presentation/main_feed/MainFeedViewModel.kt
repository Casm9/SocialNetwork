package com.casm.socialnetwork.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.presentation.PagingState
import com.casm.socialnetwork.core.presentation.util.UiEvent
import com.casm.socialnetwork.core.util.DefaultPaginator
import com.casm.socialnetwork.core.util.Event
import com.casm.socialnetwork.core.util.ParentType
import com.casm.socialnetwork.core.util.PostLiker
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val postLiker: PostLiker
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            postUseCases.getPostsForFollows(page = page)
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )

    init {
        loadNextPosts()
    }

    fun onEvent(event: MainFeedEvent) {
        when (event) {
            is MainFeedEvent.LikedPost -> {
                toggleLikeForParent(event.postId)
            }

            is MainFeedEvent.DeletePost -> {
                deletePost(event.post.id)
            }
        }
    }

    private fun deletePost(postId: String) {
        viewModelScope.launch {
            when (val result = postUseCases.deletePost(postId)) {
                is Resource.Success -> {
                    _pagingState.value = pagingState.value.copy(
                        items = pagingState.value.items.filter {
                            it.id != postId
                        }
                    )
                    _eventFlow.emit(UiEvent.ShowSnackbar(UIText.StringResource(R.string.successfully_deleted_post)))
                }

                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.uiText ?: UIText.unknownError()))
                }
            }

        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun toggleLikeForParent(
        parentId: String,
    ) {
        viewModelScope.launch {
            postLiker.toggleLike(
                posts = pagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.toggleLikeForParent(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdated = { posts ->
                    _pagingState.value = pagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }
}