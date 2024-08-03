package com.casm.socialnetwork.feature_chat.presentation.message


sealed class MessageEvent {
    data object SendMessage: MessageEvent()
    data class EnteredMessage(val message: String): MessageEvent()
}