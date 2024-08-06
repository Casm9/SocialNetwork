package com.casm.socialnetwork.feature_chat.domain.repository

import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_chat.domain.model.Chat
import com.casm.socialnetwork.feature_chat.domain.model.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getChatsForUser(): Resource<List<Chat>>

    fun observeChatEvent(): Flow<WebSocket.Event>

    fun observeMessages(): Flow<Message>

    fun sendMessage(toId: String, text: String, chatId: String?)
}