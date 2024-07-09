package com.casm.socialnetwork.feature_post.domain.use_case

import androidx.paging.PagingData
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsForFollowsUseCase(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<PagingData<Post>> {
        return repository.posts
    }
}