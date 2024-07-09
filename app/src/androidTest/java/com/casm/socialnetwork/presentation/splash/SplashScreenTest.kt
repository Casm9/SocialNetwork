package com.casm.socialnetwork.presentation.splash

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.casm.socialnetwork.core.presentation.MainActivity
import com.casm.socialnetwork.core.presentation.ui.theme.SocialNetworkTheme
import com.casm.socialnetwork.core.util.Screen
import com.casm.socialnetwork.feature_auth.presentation.splash.SplashScreen
import com.casm.socialnetwork.core.util.Constants
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @RelaxedMockK
    lateinit var navController: NavController


    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun splashScreen_displaysAndDisappears() = testDispatcher.runBlockingTest{
        composeTestRule.activity.setContent {
            SocialNetworkTheme {
                SplashScreen(
                    navController = navController,
                    dispatcher = testDispatcher
                )
            }
        }
        composeTestRule.onNodeWithContentDescription(label = "Logo")
            .assertExists()

        testScheduler.apply { advanceTimeBy(Constants.SPLASH_SCREEN_DURATION); runCurrent() }

        verify {
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }

    }
}