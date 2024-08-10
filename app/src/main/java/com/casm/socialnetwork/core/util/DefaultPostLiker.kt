package com.casm.socialnetwork.core.util

import com.casm.socialnetwork.core.domain.models.Post

class DefaultPostLiker : PostLiker {
    override suspend fun toggleLike(
        posts: List<Post>,
        parentId: String,
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        onStateUpdated: (List<Post>) -> Unit
    ) {
        val post = posts.find { it.id == parentId }
        val currentlyLiked = post?.isLiked == true
        val currentLikeCount = post?.likeCount ?: 0
        val newPosts = posts.map { newPost ->
            if (newPost.id == parentId) {
                newPost.copy(
                    isLiked = !newPost.isLiked,
                    likeCount = if (currentlyLiked) {
                        newPost.likeCount - 1
                    } else newPost.likeCount + 1
                )
            } else newPost
        }
        onStateUpdated(newPosts)
        when (onRequest(currentlyLiked)) {
            is Resource.Success -> Unit
            is Resource.Error -> {
                val oldPosts = posts.map { oldPost ->
                    if (oldPost.id == parentId) {
                        oldPost.copy(
                            isLiked = currentlyLiked,
                            likeCount = currentLikeCount
                        )
                    } else oldPost
                }
                onStateUpdated(oldPosts)
            }
        }
    }
}