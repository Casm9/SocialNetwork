package com.casm.socialnetwork.feature_profile.domain.use_case

import com.casm.socialnetwork.core.domain.models.UserItem
import com.casm.socialnetwork.core.domain.repository.ProfileRepository
import com.casm.socialnetwork.core.util.Resource

class SearchUserUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(query: String): Resource<List<UserItem>> {
        if (query.isBlank()) {
            return Resource.Success(data = emptyList())
        }
        return repository.searchUser(query)
    }
}