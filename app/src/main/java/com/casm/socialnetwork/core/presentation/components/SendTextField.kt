package com.casm.socialnetwork.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.states.StandardTextFieldState
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceLarge

@Composable
fun SendTextField(
    state: StandardTextFieldState,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    hint: String = "",
    isLoading: Boolean = false,
    canSendMessage: Boolean = true,
    focusRequester: FocusRequester = FocusRequester()
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(SpaceLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandardTextField(
            text = state.text,
            onValueChange = onValueChange,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .weight(1f),
            hint = hint,
            focusRequester = focusRequester
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            IconButton(
                onClick = onSend,
                enabled = state.error == null || !canSendMessage
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    tint = if (state.error == null && canSendMessage) {
                        MaterialTheme.colorScheme.primary
                    } else MaterialTheme.colorScheme.background,
                    contentDescription = stringResource(id = R.string.send_comment)
                )
            }
        }
    }
}