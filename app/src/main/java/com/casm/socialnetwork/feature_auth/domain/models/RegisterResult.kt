package com.casm.socialnetwork.feature_auth.domain.models

import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.feature_auth.presentation.util.AuthError

data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)