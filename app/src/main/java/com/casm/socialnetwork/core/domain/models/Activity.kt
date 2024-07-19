package com.casm.socialnetwork.core.domain.models

import com.casm.socialnetwork.feature_activity.domain.ActivityType

data class Activity(
    val userId: String,
    val parentId: String,
    val username: String,
    val activityType: ActivityType,
    val formattedTime: String
)
