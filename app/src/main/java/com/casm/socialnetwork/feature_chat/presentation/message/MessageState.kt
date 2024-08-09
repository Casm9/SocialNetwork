package com.casm.socialnetwork.feature_chat.presentation.message

import com.casm.socialnetwork.feature_chat.domain.model.Message

data class MessageState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val canSendMessage: Boolean = false
)
