package com.casm.socialnetwork.feature_profile.domain.use_case

import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.core.domain.repository.ProfileRepository

class ToggleFollowStateForUserUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: String, isFollowing: Boolean): SimpleResource {
        return if (isFollowing) {
            repository.unfollowUser(userId)
        } else {
            repository.followUser(userId)
        }
    }
}