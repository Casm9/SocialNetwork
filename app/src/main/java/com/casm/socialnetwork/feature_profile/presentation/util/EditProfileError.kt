package com.casm.socialnetwork.feature_profile.presentation.util


sealed class EditProfileError: Error() {
    data object FieldEmpty: EditProfileError()
}
