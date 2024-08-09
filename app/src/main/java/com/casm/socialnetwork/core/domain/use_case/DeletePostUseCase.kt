package com.casm.socialnetwork.core.domain.use_case

import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository

class DeletePostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String): SimpleResource {
        return repository.deletePost(postId)
    }
}