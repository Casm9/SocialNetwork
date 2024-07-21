package com.casm.socialnetwork.feature_post.domain.use_case

data class PostUseCases(
    val getPostsForFollowsUseCase: GetPostsForFollowsUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getPostDetails: GetPostDetailsUseCase,
    val getCommentsForPost: GetCommentsForPostUseCase
)
