package com.casm.socialnetwork.feature_post.domain.use_case

import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository

class CreateCommentUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String, comment: String): SimpleResource {
        if (comment.isBlank()) {
            return Resource.Error(UIText.StringResource(R.string.error_field_empty))
        }
        if (postId.isBlank()) {
            return Resource.Error(UIText.unknownError())
        }
        return repository.createComment(postId, comment)
    }
}