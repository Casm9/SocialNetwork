package com.casm.socialnetwork.feature_chat.presentation.message

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casm.socialnetwork.core.domain.states.StandardTextFieldState
import com.casm.socialnetwork.core.presentation.PagingState
import com.casm.socialnetwork.core.presentation.util.UiEvent
import com.casm.socialnetwork.core.util.DefaultPaginator
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_chat.domain.model.Message
import com.casm.socialnetwork.feature_chat.domain.use_case.ChatUseCases
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messageTextFieldState = mutableStateOf(StandardTextFieldState())
    val messageTextFieldState: State<StandardTextFieldState> = _messageTextFieldState

    private val _pagingState = mutableStateOf<PagingState<Message>>(PagingState())
    val pagingState: State<PagingState<Message>> = _pagingState

    private val _state = mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    private val _eventFLow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFLow.asSharedFlow()

    private val _messageUpdatedEvent = MutableSharedFlow<MessageUpdateEvent>(replay = 1)
    val messageUpdatedEvent = _messageUpdatedEvent.asSharedFlow()

    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(isLoading = isLoading)
        },
        onRequest = { nextPage ->
            savedStateHandle.get<String>("chatId")?.let { chatId ->
                chatUseCases.getMessagesForChat(
                    chatId, nextPage
                )
            } ?: Resource.Error(UIText.unknownError())
        },
        onError = { errorUiText ->
            _eventFLow.emit(UiEvent.ShowSnackbar(errorUiText))
        },
        onSuccess = { messages ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + messages,
                endReached = messages.isEmpty(),
                isLoading = false
            )

            viewModelScope.launch {
                _messageUpdatedEvent.emit(MessageUpdateEvent.MessagePageLoaded)
            }
        }
    )

    init {
        chatUseCases.initializeRepository()
        loadNextMessages()
        observeChatMessages()
        observeChatEvents()
    }

    private fun observeChatMessages() {
        viewModelScope.launch {
            chatUseCases.observeMessages().collect { message ->
                _pagingState.value = pagingState.value.copy(
                    items = pagingState.value.items + message
                )
                _messageUpdatedEvent.emit(MessageUpdateEvent.SingleMessageUpdate)
            }
        }
    }

    private fun observeChatEvents() {
        chatUseCases.observeChatEvents().onEach { event ->
            when (event) {
                is WebSocket.Event.OnConnectionOpened<*> -> {
                    println("Connection was opened")
                }

                is WebSocket.Event.OnConnectionFailed -> {
                    println("Connection failed: ${event.throwable}")
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun loadNextMessages() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun sendMessage() {
        val toId = savedStateHandle.get<String>("remoteUserId") ?: return
        if (messageTextFieldState.value.text.isBlank()) {
            return
        }
        val chatId = savedStateHandle.get<String>("chatId")
        chatUseCases.sendMessage(toId, messageTextFieldState.value.text, chatId)

        _messageTextFieldState.value = StandardTextFieldState()

        viewModelScope.launch {
            _messageUpdatedEvent.emit(MessageUpdateEvent.MessageSent)
        }

    }

    fun onEvent(event: MessageEvent) {
        when (event) {
            is MessageEvent.EnteredMessage -> {
                _messageTextFieldState.value = messageTextFieldState.value.copy(
                    text = event.message
                )
            }

            is MessageEvent.SendMessage -> {
                sendMessage()
            }
        }
    }

    sealed class MessageUpdateEvent {
        data object SingleMessageUpdate: MessageUpdateEvent()
        data object MessagePageLoaded: MessageUpdateEvent()
        data object MessageSent: MessageUpdateEvent()
    }
}