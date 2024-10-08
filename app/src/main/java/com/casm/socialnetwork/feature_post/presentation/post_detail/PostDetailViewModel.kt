package com.casm.socialnetwork.feature_post.presentation.post_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.states.StandardTextFieldState
import com.casm.socialnetwork.core.presentation.util.UiEvent
import com.casm.socialnetwork.core.util.ParentType
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_auth.domain.use_case.AuthenticateUseCase
import com.casm.socialnetwork.feature_post.domain.use_case.PostUseCases
import com.casm.socialnetwork.feature_post.presentation.util.CommentError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
    private val postUseCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _commentTextFieldState =
        mutableStateOf(StandardTextFieldState(error = CommentError.FieldEmpty))
    val commentTextFieldState: State<StandardTextFieldState> = _commentTextFieldState

    private val _commentState = mutableStateOf(CommentState())
    val commentState: State<CommentState> = _commentState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var isUserLoggedIn = false

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            loadPostDetails(postId)
            loadCommentsForPost(postId)
        }
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.LikePost -> {
                val isLiked = state.value.post?.isLiked == true
                toggleLikeForParent(
                    parentId = state.value.post?.id ?: return,
                    parentType = ParentType.Post.type,
                    isLiked = isLiked
                )
            }

            is PostDetailEvent.Comment -> {
                createComment(
                    postId = savedStateHandle.get<String>("postId") ?: "",
                    comment = commentTextFieldState.value.text
                )
            }

            is PostDetailEvent.LikeComment -> {
                val isLiked = state.value.comments.find {
                    it.id == event.commentId
                }?.isLiked == true
                toggleLikeForParent(
                    parentId = event.commentId,
                    parentType = ParentType.Comment.type,
                    isLiked = isLiked
                )
            }

            is PostDetailEvent.EnteredComment -> {
                _commentTextFieldState.value = commentTextFieldState.value.copy(
                    text = event.comment,
                    error = if (event.comment.isBlank()) CommentError.FieldEmpty else null
                )
            }
        }
    }

    private fun toggleLikeForParent(
        parentId: String,
        parentType: Int,
        isLiked: Boolean
    ) {
        viewModelScope.launch {
            isUserLoggedIn = authenticate() is Resource.Success
            if (!isUserLoggedIn) {
                _eventFlow.emit(UiEvent.ShowSnackbar(UIText.StringResource(R.string.error_not_logged_in)))
                return@launch
            }
            val currentLikeCount = state.value.post?.likeCount ?: 0
            when (parentType) {
                ParentType.Post.type -> {
                    val post = state.value.post
                    _state.value = state.value.copy(
                        post = state.value.post?.copy(
                            isLiked = !isLiked,
                            likeCount = if (isLiked) {
                                post?.likeCount?.minus(1) ?: 0
                            } else post?.likeCount?.plus(1) ?: 0
                        )
                    )
                }

                ParentType.Comment.type -> {
                    _state.value = state.value.copy(
                        comments = state.value.comments.map { comment ->
                            if (comment.id == parentId) {
                                comment.copy(
                                    isLiked = !isLiked,
                                    likeCount = if (isLiked) {
                                        comment.likeCount - 1
                                    } else comment.likeCount + 1
                                )
                            } else comment
                        }
                    )
                }
            }
            val result = postUseCases.toggleLikeForParent(
                parentId = parentId,
                parentType = parentType,
                isLiked = isLiked
            )
            when (result) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    when (parentType) {
                        ParentType.Post.type -> {
                            _state.value = state.value.copy(
                                post = state.value.post?.copy(
                                    isLiked = isLiked,
                                    likeCount = currentLikeCount
                                )
                            )
                        }

                        ParentType.Comment.type -> {
                            _state.value = state.value.copy(
                                comments = state.value.comments.map { comment ->
                                    if (comment.id == parentId) {
                                        comment.copy(
                                            isLiked = isLiked,
                                            likeCount = if (comment.isLiked) {
                                                comment.likeCount - 1
                                            } else comment.likeCount + 1
                                        )
                                    } else comment
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun createComment(postId: String, comment: String) {
        viewModelScope.launch {
            isUserLoggedIn = authenticate() is Resource.Success
            if (!isUserLoggedIn) {
                _eventFlow.emit(UiEvent.ShowSnackbar(UIText.StringResource(R.string.error_not_logged_in)))
                return@launch
            }
            _commentState.value = commentState.value.copy(
                isLoading = true
            )
            val result = postUseCases.createComment(
                postId = postId,
                comment = comment
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = UIText.StringResource(R.string.comment_posted)
                        )
                    )
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                    _commentTextFieldState.value = commentTextFieldState.value.copy(
                        text = "",
                        error = CommentError.FieldEmpty
                    )
                    loadCommentsForPost(postId)
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UIText.unknownError()
                        )
                    )
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadPostDetails(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingPost = true
            )
            when (val result = postUseCases.getPostDetails(postId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        post = result.data,
                        isLoadingPost = false
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            result.uiText ?: UIText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun loadCommentsForPost(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingComments = true
            )
            when (val result = postUseCases.getCommentsForPost(postId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        comments = result.data ?: emptyList(),
                        isLoadingComments = false
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingComments = false
                    )
                }
            }
        }
    }
}