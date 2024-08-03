package com.casm.socialnetwork.feature_chat.domain.model

data class Chat(
    val remoteUserUsername: String,
    val remoteUserProfileUrl: String,
    val lastMessage: String,
    val lastMessageFormattedTime: String
)
