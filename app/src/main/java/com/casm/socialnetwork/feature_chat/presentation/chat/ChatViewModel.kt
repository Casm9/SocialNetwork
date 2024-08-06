package com.casm.socialnetwork.feature_chat.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casm.socialnetwork.core.presentation.util.UiEvent
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_chat.domain.use_case.ChatUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases
): ViewModel() {

    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    private val _eventFLow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFLow.asSharedFlow()

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val result = chatUseCases.getChatsForUser()
            when(result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        chats = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFLow.emit(UiEvent.ShowSnackbar(result.uiText ?: UIText.unknownError()))
                    _state.value = state.value.copy(isLoading = false)
                }
            }
        }
    }
}