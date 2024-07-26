package com.casm.socialnetwork.feature_profile.domain.use_case

import androidx.paging.PagingData
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.domain.repository.ProfileRepository
import com.casm.socialnetwork.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPostsForProfileUseCase (
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: String, page: Int): Resource<List<Post>> {
        return repository.getPostsPaged(
            userId = userId,
            page = page
        )
    }
}