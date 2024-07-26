package com.casm.socialnetwork.feature_post.presentation.person_list

import com.casm.socialnetwork.core.util.Event


sealed class PostEvent: Event() {
    data object OnLiked: PostEvent()
}
