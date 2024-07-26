package com.casm.socialnetwork.feature_post.presentation.main_feed

import com.casm.socialnetwork.core.domain.models.Post

sealed class MainFeedEvent {
    data class DeletePost(val post: Post): MainFeedEvent()
    data class LikedPost(val postId: String): MainFeedEvent()

}
