package com.casm.socialnetwork.feature_profile.presentation.util

import com.casm.socialnetwork.core.util.Error
sealed class EditProfileError: Error() {
    data object FieldEmpty: EditProfileError()
}
