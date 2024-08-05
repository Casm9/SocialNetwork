package com.casm.socialnetwork.feature_chat.domain.model

data class Message(
    val fromId: String,
    val toId: String,
    val chatId: String?,
    val text: String,
    val formattedTime: String,
)
