package com.casm.socialnetwork.feature_chat.di

import com.casm.socialnetwork.feature_chat.data.remote.ChatApi
import com.casm.socialnetwork.feature_chat.data.repository.ChatRepositoryImpl
import com.casm.socialnetwork.feature_chat.domain.repository.ChatRepository
import com.casm.socialnetwork.feature_chat.domain.use_case.ChatUseCases
import com.casm.socialnetwork.feature_chat.domain.use_case.GetChatsForUser
import com.casm.socialnetwork.feature_chat.domain.use_case.GetMessagesForChat
import com.casm.socialnetwork.feature_chat.domain.use_case.InitializeRepository
import com.casm.socialnetwork.feature_chat.domain.use_case.ObserveChatEvents
import com.casm.socialnetwork.feature_chat.domain.use_case.ObserveMessages
import com.casm.socialnetwork.feature_chat.domain.use_case.SendMessage
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
    fun provideChatUseCases(repository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            sendMessage = SendMessage(repository),
            observeChatEvents = ObserveChatEvents(repository),
            observeMessages = ObserveMessages(repository),
            getChatsForUser = GetChatsForUser(repository),
            getMessagesForChat = GetMessagesForChat(repository),
            initializeRepository = InitializeRepository(repository)
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
    fun provideChatRepository(chatApi: ChatApi, client: OkHttpClient): ChatRepository {
        return ChatRepositoryImpl(chatApi, client)
    }
}