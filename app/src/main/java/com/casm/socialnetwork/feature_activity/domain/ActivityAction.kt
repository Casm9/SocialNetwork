package com.casm.socialnetwork.feature_activity.domain

sealed class ActivityAction {
    data object LikedPost: ActivityAction()
    data object CommentedOnPost: ActivityAction()
    data object FollowedYou: ActivityAction()
}