package com.casm.socialnetwork.feature_profile.presentation.search

import com.casm.socialnetwork.core.domain.models.UserItem

data class SearchState(
    val userItems: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)