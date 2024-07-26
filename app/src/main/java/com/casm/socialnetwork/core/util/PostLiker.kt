package com.casm.socialnetwork.core.util

import com.casm.socialnetwork.core.domain.models.Post

interface PostLiker {
    suspend fun toggleLike(
        posts: List<Post>,
        parentId: String,
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        onStateUpdated: (List<Post>) -> Unit
    )
}