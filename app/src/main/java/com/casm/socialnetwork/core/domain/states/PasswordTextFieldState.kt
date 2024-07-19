package com.casm.socialnetwork.core.domain.states

import com.casm.socialnetwork.core.util.Error

data class PasswordTextFieldState(
    val text: String = "",
    val error: Error? = null,
    val isPasswordVisible: Boolean = false
)