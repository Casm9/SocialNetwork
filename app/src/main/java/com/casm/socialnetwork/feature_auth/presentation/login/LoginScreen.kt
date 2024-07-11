package com.casm.socialnetwork.feature_auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.presentation.components.StandardTextField
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceLarge
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceSmall
import com.casm.socialnetwork.core.presentation.util.UiEvent
import com.casm.socialnetwork.core.presentation.util.asString
import com.casm.socialnetwork.core.util.Screen
import com.casm.socialnetwork.feature_auth.presentation.util.AuthError
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    onNavigate: (String) -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val state = viewModel.loginState.value
    val context = LocalContext.current
    
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }

                else -> {}
            }
        }
    }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(
                start = SpaceLarge,
                end = SpaceLarge,
                top = SpaceLarge,
                bottom = 50.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = emailState.text,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredEmail(it))
                },
                keyboardType = KeyboardType.Email,
                error = when (emailState.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                hint = stringResource(id = R.string.login_hint)
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            StandardTextField(
                text = passwordState.text,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                hint = stringResource(id = R.string.password_hint),
                keyboardType = KeyboardType.Password,
                error = when (passwordState.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                isPasswordVisible = state.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Button(
                onClick = {
                    viewModel.onEvent(LoginEvent.Login)
                },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            if(state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                val signUpText = stringResource(id = R.string.sign_up)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    onNavigate(Screen.RegisterScreen.route)
                }
        )
    }
}
