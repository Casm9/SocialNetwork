package com.casm.socialnetwork.feature_post.domain.use_case

import com.casm.socialnetwork.core.domain.models.Comment
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository

class GetCommentsForPostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String): Resource<List<Comment>> {
        return repository.getCommentsForPost(postId)
    }
}