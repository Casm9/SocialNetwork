package com.casm.socialnetwork.feature_activity.domain

sealed class ActivityType(val type: Int) {
    data object LikedPost : ActivityType(0)
    data object LikedComment : ActivityType(1)
    data object CommentedOnPost : ActivityType(2)
    data object FollowedUser : ActivityType(3)
}