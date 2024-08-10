package com.casm.socialnetwork.feature_activity.presentation

sealed class ActivityEvent {
    data class ClickedOnUser(val userId: String) : ActivityEvent()
    data class ClickedOnParent(val userId: String) : ActivityEvent()
}
