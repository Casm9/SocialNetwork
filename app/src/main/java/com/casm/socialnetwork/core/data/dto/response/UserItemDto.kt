package com.casm.socialnetwork.core.data.dto.response

import com.casm.socialnetwork.core.domain.models.UserItem

data class UserItemDto(
    val userId: String,
    val username: String,
    val profilePictureUrl: String,
    val bio: String,
    val isFollowing: Boolean
) {
    fun toUserItem(): UserItem {
        return UserItem(
            userId = userId,
            profilePictureUrl = profilePictureUrl,
            username = username,
            bio = bio,
            isFollowing = isFollowing
        )
    }
}
