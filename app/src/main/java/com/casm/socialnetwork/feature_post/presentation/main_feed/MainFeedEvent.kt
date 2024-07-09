package com.casm.socialnetwork.feature_post.presentation.main_feed

sealed class MainFeedEvent {
    data object LoadMorePosts: MainFeedEvent()
    data object LoadedPage: MainFeedEvent()
}
