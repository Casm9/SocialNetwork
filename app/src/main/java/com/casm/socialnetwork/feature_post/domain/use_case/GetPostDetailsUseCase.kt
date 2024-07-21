package com.casm.socialnetwork.feature_post.domain.use_case

import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository

class GetPostDetailsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String): Resource<Post> {
        return repository.getPostDetails(postId)
    }
}