package com.casm.socialnetwork.feature_chat.domain.use_case

import com.casm.socialnetwork.feature_chat.domain.model.Message
import com.casm.socialnetwork.feature_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ObserveMessages(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<Message> {
        return repository.observeMessages()
    }
}