package com.casm.socialnetwork.core.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.models.BottomNavItem
import com.casm.socialnetwork.core.util.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    state: ScaffoldState,
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.MainFeedScreen.route,
            icon = Icons.Outlined.Home,
            contentDescription = "Home",
        ),
        BottomNavItem(
            route = Screen.ChatScreen.route,
            icon = Icons.Outlined.Message,
            contentDescription = "Message",
        ),
        BottomNavItem(route = "_"),
        BottomNavItem(
            route = Screen.ActivityScreen.route,
            icon = Icons.Outlined.Notifications,
            contentDescription = "Activity",
        ),
        BottomNavItem(
            route = Screen.ProfileScreen.route,
            icon = Icons.Outlined.Person,
            contentDescription = "Profile",
        )
    ),
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
       bottomBar = {
           if (showBottomBar) {
               BottomAppBar(
                   modifier = Modifier.fillMaxWidth(),
                   backgroundColor = MaterialTheme.colorScheme.surface,
                   cutoutShape = CircleShape,
                   elevation = 5.dp
               ) {
                   BottomNavigation (
                       backgroundColor = MaterialTheme.colorScheme.surface
                   ) {
                       bottomNavItems.forEachIndexed { i, bottomNavItem ->
                           StandardBottomNavItem(
                               icon = bottomNavItem.icon,
                               contentDescription = bottomNavItem.contentDescription,
                               selected = navController.currentDestination?.route?.startsWith(bottomNavItem.route) == true,
                               alertCount = bottomNavItem.alertCount,
                               enabled = bottomNavItem.icon != null
                           ) {
                               if (navController.currentDestination?.route != bottomNavItem.route) {
                                   navController.navigate(bottomNavItem.route)
                               }
                           }
                       }
                   }
               }
           }
       },
        scaffoldState = state,
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    onClick = onFabClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = Color.Black,
                        contentDescription = stringResource(id = R.string.make_post)
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier
    ) {
        content()
    }
    
}