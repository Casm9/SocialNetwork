package com.casm.socialnetwork.feature_post.presentation.util

import com.casm.socialnetwork.core.util.Error

sealed class PostDescriptionError: Error() {
    data object FieldEmpty: PostDescriptionError()
}
