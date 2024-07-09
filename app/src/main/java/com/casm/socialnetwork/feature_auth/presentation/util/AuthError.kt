package com.casm.socialnetwork.feature_auth.presentation.util



sealed class AuthError: Error() {
    data object FieldEmpty: AuthError()
    data object InputTooShort: AuthError()
    data object InvalidEmail: AuthError()
    data object InvalidPassword: AuthError()
}
