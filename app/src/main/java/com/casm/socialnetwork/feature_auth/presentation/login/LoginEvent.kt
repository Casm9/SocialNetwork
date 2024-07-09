package com.casm.socialnetwork.feature_auth.presentation.login

sealed class LoginEvent {
    data class EnteredEmail(val email: String): LoginEvent()
    data class EnteredPassword(val password: String): LoginEvent()
    data object Login: LoginEvent()
    data object TogglePasswordVisibility: LoginEvent()
}
