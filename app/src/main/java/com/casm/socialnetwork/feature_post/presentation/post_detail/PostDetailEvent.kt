package com.casm.socialnetwork.feature_post.presentation.post_detail

sealed class PostDetailEvent() {
    data object LikePost: PostDetailEvent()
    data object Comment: PostDetailEvent()
    data class LikeComment(val commentId: String): PostDetailEvent()
    data class EnteredComment(val comment: String): PostDetailEvent()
    data object SharePost: PostDetailEvent()
}
