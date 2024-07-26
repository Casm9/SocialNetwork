package com.casm.socialnetwork.feature_profile.presentation.profile

import com.casm.socialnetwork.core.domain.models.Post

sealed class ProfileEvent {
    data class GetProfile(val userId: String): ProfileEvent()
    data class LikePost(val postId: String): ProfileEvent()
    data class DeletePost(val post: Post): ProfileEvent()
    data object DismissLogoutDialog: ProfileEvent()
    data object ShowLogoutDialog: ProfileEvent()
    data object Logout: ProfileEvent()

}