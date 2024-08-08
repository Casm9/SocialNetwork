package com.casm.socialnetwork.feature_chat.domain.use_case

data class ChatUseCases(
    val sendMessage: SendMessage,
    val observeMessages: ObserveMessages,
    val observeChatEvents: ObserveChatEvents,
    val getChatsForUser: GetChatsForUser,
    val getMessagesForChat: GetMessagesForChat,
    val initializeRepository: InitializeRepository
)