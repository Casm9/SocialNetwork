package com.casm.socialnetwork.core.domain.states

import com.casm.socialnetwork.core.util.Error

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)