package com.casm.socialnetwork.core.presentation.util

import com.casm.socialnetwork.core.util.Event
import com.casm.socialnetwork.core.util.UIText

sealed class UiEvent: Event() {
    data class ShowSnackbar(val uiText: UIText): UiEvent()
    data class Navigate(val route: String): UiEvent()
    data object NavigateUp: UiEvent()
}