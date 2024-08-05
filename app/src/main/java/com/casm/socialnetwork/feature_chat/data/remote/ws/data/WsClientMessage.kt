package com.casm.socialnetwork.feature_chat.data.remote.ws.data

data class WsClientMessage(
    val toId: String,
    val text: String,
    val chatId: String?,
)