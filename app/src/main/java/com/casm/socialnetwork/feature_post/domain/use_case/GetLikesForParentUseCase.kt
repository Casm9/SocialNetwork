package com.casm.socialnetwork.feature_post.domain.use_case

import com.casm.socialnetwork.core.domain.models.UserItem
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository

class GetLikesForParentUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(parentId: String): Resource<List<UserItem>> {
        return repository.getLikesForParent(parentId)
    }
}