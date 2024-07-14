package com.casm.socialnetwork.core.presentation.components


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.util.Screen
import com.casm.socialnetwork.feature_profile.presentation.edit_profile.EditProfileScreen
import com.casm.socialnetwork.feature_activity.presentation.ActivityScreen
import com.casm.socialnetwork.feature_chat.presentation.chat.ChatScreen
import com.casm.socialnetwork.feature_post.presentation.create_post.CreatePostScreen
import com.casm.socialnetwork.feature_auth.presentation.login.LoginScreen
import com.casm.socialnetwork.feature_auth.presentation.register.RegisterScreen
import com.casm.socialnetwork.feature_auth.presentation.splash.SplashScreen
import com.casm.socialnetwork.feature_post.presentation.main_feed.MainFeedScreen
import com.casm.socialnetwork.feature_post.presentation.person_list.PersonListScreen
import com.casm.socialnetwork.feature_post.presentation.post_detail.PostDetailScreen
import com.casm.socialnetwork.feature_profile.presentation.profile.ProfileScreen
import com.casm.socialnetwork.feature_profile.presentation.search.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
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
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                onPopBackStack = navController::popBackStack,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.MainFeedScreen.route) {
            MainFeedScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.ChatScreen.route) {
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(Screen.ActivityScreen.route) {
            ActivityScreen(
                onNavigateUp = navController::navigateUp,
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
                userId = it.arguments?.getString("userId") ?: "",
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
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
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.CreatePostScreen.route) {
            CreatePostScreen(
                onNavigateUp = navController::navigateUp,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(Screen.PostDetailScreen.route) {
            PostDetailScreen(
                onNavigateUp = navController::navigateUp,
                post = Post(
                    username = "Casm",
                    imageUrl = "",
                    profilePictureUrl = "",
                    description = "Test test test test test test test\n" +
                            "test test test test \n" +
                            "test test tes test... " + "Test test test test test test test\n" +
                            "test test test test \n" +
                            "test test tes test... ",
                    likeCount = 16,
                    commentCount = 7
                )
            )
        }
        composable(Screen.PersonListScreen.route) {
            PersonListScreen(
                onNavigateUp = navController::navigateUp,
            )
        }
    }
}