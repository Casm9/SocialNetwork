package com.casm.socialnetwork.feature_chat.data.repository

import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_chat.data.remote.ChatApi
import com.casm.socialnetwork.feature_chat.data.remote.ChatService
import com.casm.socialnetwork.feature_chat.data.remote.data.WsClientMessage
import com.casm.socialnetwork.feature_chat.domain.model.Chat
import com.casm.socialnetwork.feature_chat.domain.model.Message
import com.casm.socialnetwork.feature_chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class ChatRepositoryImpl(
    private val chatService: ChatService,
    private val chatApi: ChatApi
): ChatRepository {
    override suspend fun getChatsForUser(): Resource<List<Chat>> {
        return try {
            val chats = chatApi
                .getChatsForUser()
                .mapNotNull { it.toChat() }
            Resource.Success(data = chats)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UIText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UIText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override fun observeChatEvent(): Flow<WebSocket.Event> {
        return chatService.observeEvents()
    }

    override fun observeMessages(): Flow<Message> {
        return chatService.observeMessages().map { it.toMessage() }
    }

    override fun sendMessage(toId: String, text: String, chatId: String?) {
       chatService.sendMessage(
           WsClientMessage(
               toId = toId,
               text = text,
               chatId = chatId
           )
       )
    }
}