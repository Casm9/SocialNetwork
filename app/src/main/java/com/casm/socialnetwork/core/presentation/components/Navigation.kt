package com.casm.socialnetwork.core.presentation.components

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.ImageLoader
import com.casm.socialnetwork.core.util.Screen
import com.casm.socialnetwork.feature_activity.presentation.ActivityScreen
import com.casm.socialnetwork.feature_auth.presentation.login.LoginScreen
import com.casm.socialnetwork.feature_auth.presentation.register.RegisterScreen
import com.casm.socialnetwork.feature_auth.presentation.splash.SplashScreen
import com.casm.socialnetwork.feature_chat.presentation.chat.ChatScreen
import com.casm.socialnetwork.feature_chat.presentation.message.MessageScreen
import com.casm.socialnetwork.feature_post.presentation.create_post.CreatePostScreen
import com.casm.socialnetwork.feature_post.presentation.main_feed.MainFeedScreen
import com.casm.socialnetwork.feature_post.presentation.person_list.PersonListScreen
import com.casm.socialnetwork.feature_post.presentation.post_detail.PostDetailScreen
import com.casm.socialnetwork.feature_profile.presentation.edit_profile.EditProfileScreen
import com.casm.socialnetwork.feature_profile.presentation.profile.ProfileScreen
import com.casm.socialnetwork.feature_profile.presentation.search.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState,
                onLogin = {
                    navController.popBackStack(
                        route = Screen.MainFeedScreen.route,
                        inclusive = true
                    )
                    navController.navigate(route = Screen.MainFeedScreen.route)
                }
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                navController = navController,
                onPopBackStack = navController::popBackStack,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
        composable(Screen.ChatScreen.route) {
            ChatScreen(
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.MessagesScreen.route + "/{remoteUserId}/{remoteUsername}/{remoteUserProfilePictureUrl}?chatId={chatId}",
            arguments = listOf(
                navArgument("chatId") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("remoteUserId") {
                    type = NavType.StringType
                },
                navArgument("remoteUsername") {
                    type = NavType.StringType
                },
                navArgument("remoteUserProfilePictureUrl") {
                    type = NavType.StringType
                }
            )
        ) {
            val remoteUserId = it.arguments?.getString("remoteUserId")!!
            val remoteUsername = it.arguments?.getString("remoteUsername")!!
            val remoteUserProfilePictureUrl =
                it.arguments?.getString("remoteUserProfilePictureUrl")!!
            MessageScreen(
                remoteUserId = remoteUserId,
                remoteUsername = remoteUsername,
                encodedRemoteUserProfilePictureUrl = remoteUserProfilePictureUrl,
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader
            )
        }
        composable(Screen.ActivityScreen.route) {
            ActivityScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(
            route = Screen.ProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            ProfileScreen(
                userId = it.arguments?.getString("userId"),
                onNavigate = navController::navigate,
                onLogout = {
                    navController.navigate(route = Screen.LoginScreen.route)
                },
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                }
            )
        ) {
            EditProfileScreen(
                onNavigateUp = navController::navigateUp,
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }
        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(
                onNavigateUp = navController::navigateUp,
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.PostDetailScreen.route + "/{postId}?shouldShowKeyboard={shouldShowKeyboard}",
            arguments = listOf(
                navArgument(
                    name = "postId"
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = "shouldShowKeyboard"
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = "https://casm.com/{postId}"
                }
            )
        ) {
            val shouldShowKeyboard = it.arguments?.getBoolean("shouldShowKeyboard") ?: false
            PostDetailScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                shouldShowKeyboard = shouldShowKeyboard,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.PersonListScreen.route + "/{parentId}",
            arguments = listOf(
                navArgument("parentId") {
                    type = NavType.StringType
                }
            )
        ) {
            PersonListScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
    }
}