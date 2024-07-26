package com.casm.socialnetwork.core.domain.use_case

import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository

class ToggleLikeForParentUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(parentId: String, parentType: Int, isLiked: Boolean): SimpleResource {
        return if (!isLiked) {
            repository.likeParent(parentId, parentType)
        } else {
            repository.unlikeParent(parentId, parentType)
        }
    }
}