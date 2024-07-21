package com.casm.socialnetwork.feature_post.presentation.post_detail

sealed class PostDetailEvent() {
    data object LikePost: PostDetailEvent()
    data class Comment(val comment: String) : PostDetailEvent()
    data class LikeComment(val commentId: String): PostDetailEvent()
    data object SharePost: PostDetailEvent()
}
