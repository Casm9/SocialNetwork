package com.casm.socialnetwork.feature_post.domain.use_case

import androidx.paging.PagingData
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.util.Constants
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsForFollowsUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int = Constants.DEFAULT_PAGE_SIZE): Resource<List<Post>> {
        return repository.getPostsForFollows(page, pageSize)
    }
}