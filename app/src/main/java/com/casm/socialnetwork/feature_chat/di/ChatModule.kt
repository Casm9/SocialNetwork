package com.casm.socialnetwork.feature_chat.di

import com.casm.socialnetwork.feature_chat.data.remote.ChatApi
import com.casm.socialnetwork.feature_chat.data.remote.ChatService
import com.casm.socialnetwork.feature_chat.data.remote.util.CustomGsonMessageAdapter
import com.casm.socialnetwork.feature_chat.data.remote.util.FlowStreamAdapter
import com.casm.socialnetwork.feature_chat.data.repository.ChatRepositoryImpl
import com.casm.socialnetwork.feature_chat.domain.repository.ChatRepository
import com.casm.socialnetwork.feature_chat.domain.use_case.ChatUseCases
import com.casm.socialnetwork.feature_chat.domain.use_case.GetChatsForUser
import com.casm.socialnetwork.feature_chat.domain.use_case.GetMessagesForChat
import com.casm.socialnetwork.feature_chat.domain.use_case.ObserveChatEvents
import com.casm.socialnetwork.feature_chat.domain.use_case.ObserveMessages
import com.casm.socialnetwork.feature_chat.domain.use_case.SendMessage
import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideScarlet(gson: Gson, client: OkHttpClient): Scarlet {
        return Scarlet.Builder()
            .addMessageAdapterFactory(CustomGsonMessageAdapter.Factory(gson))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .webSocketFactory(client.newWebSocketFactory("ws://10.0.2.2:8001/api/chat/websocket"))
            .build()
    }

    @Provides
    @Singleton
    fun provideChatService(scarlet: Scarlet): ChatService {
        return scarlet.create()
    }

    @Provides
    @Singleton
    fun provideChatUseCases(repository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            sendMessage = SendMessage(repository),
            observeChatEvents = ObserveChatEvents(repository),
            observeMessages = ObserveMessages(repository),
            getChatsForUser = GetChatsForUser(repository),
            getMessagesForChat = GetMessagesForChat(repository)
        )
    }

    @Provides
    @Singleton
    fun provideChatApi(client: OkHttpClient): ChatApi {
        return Retrofit.Builder()
            .baseUrl(ChatApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideChatRepository(chatService: ChatService, chatApi: ChatApi): ChatRepository {
        return ChatRepositoryImpl(chatService, chatApi)
    }
}