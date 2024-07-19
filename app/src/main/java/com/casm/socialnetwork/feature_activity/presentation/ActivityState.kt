package com.casm.socialnetwork.feature_activity.presentation

import com.casm.socialnetwork.core.domain.models.Activity

data class ActivityState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false
)
