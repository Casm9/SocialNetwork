package com.casm.socialnetwork.feature_post.presentation.util



sealed class PostDescriptionError: Error() {
    data object FieldEmpty: PostDescriptionError()

}
