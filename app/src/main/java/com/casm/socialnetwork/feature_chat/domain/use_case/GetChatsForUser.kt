package com.casm.socialnetwork.feature_chat.domain.use_case

import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_chat.domain.model.Chat
import com.casm.socialnetwork.feature_chat.domain.repository.ChatRepository

class GetChatsForUser(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(): Resource<List<Chat>> {
        return repository.getChatsForUser()
    }
}