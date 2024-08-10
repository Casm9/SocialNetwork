package com.casm.socialnetwork.feature_auth.presentation.util

import com.casm.socialnetwork.core.util.Error

sealed class AuthError : Error() {
    data object FieldEmpty : AuthError()
    data object InputTooShort : AuthError()
    data object InvalidEmail : AuthError()
    data object InvalidPassword : AuthError()
}
