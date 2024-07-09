package com.casm.socialnetwork.core.domain.models

import com.casm.socialnetwork.feature_activity.domain.ActivityAction

data class Activity(
    val username: String,
    val actionType: ActivityAction,
    val formattedTime: String
)
