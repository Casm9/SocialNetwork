package com.casm.socialnetwork.feature_post.presentation.util

import com.casm.socialnetwork.core.util.Error

sealed class CommentError: Error() {
    data object FieldEmpty: CommentError()
}
