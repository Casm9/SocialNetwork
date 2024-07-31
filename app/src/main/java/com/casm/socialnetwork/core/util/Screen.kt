package com.casm.socialnetwork.core.util

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splash_screen")
    data object LoginScreen: Screen("login_screen")
    data object RegisterScreen: Screen("register_screen")
    data object MainFeedScreen: Screen("main_feed_screen")
    data object PostDetailScreen: Screen("post_detail_screen")
    data object ChatScreen: Screen("chat_screen")
    data object MessagesScreen: Screen("messages_screen")
    data object ProfileScreen: Screen("profile_screen")
    data object EditProfileScreen: Screen("edit_profile_screen")
    data object PersonListScreen: Screen("person_list_screen")
    data object CreatePostScreen: Screen("create_post_screen")
    data object ActivityScreen: Screen("activity_screen")
    data object SearchScreen: Screen("search_screen")
}