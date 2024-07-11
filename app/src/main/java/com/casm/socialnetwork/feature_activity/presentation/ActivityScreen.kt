package com.casm.socialnetwork.feature_activity.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.models.Activity
import com.casm.socialnetwork.feature_activity.domain.ActivityAction
import com.casm.socialnetwork.core.util.DateFormatUtil
import com.casm.socialnetwork.feature_activity.presentation.components.ActivityItem
import com.casm.socialnetwork.core.presentation.components.StandardToolBar
import com.casm.socialnetwork.core.presentation.ui.theme.SpaceMedium
import kotlin.random.Random

@Composable
fun ActivityScreen(
    onNavigateUp: () -> Unit = {},
    viewModel: ActivityViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_activity),
                    fontWeight = FontWeight.Bold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(SpaceMedium)
        ) {
            items(20) {
                ActivityItem(
                    activity = Activity(
                        "Casm",
                        actionType = if (Random.nextInt(2) == 0) {
                            ActivityAction.LikedPost
                        } else ActivityAction.CommentedOnPost,
                        formattedTime = DateFormatUtil.timestampToFormattedString(
                            timestamp = System.currentTimeMillis(),
                            pattern = "MMM dd, HH:mm"
                        )
                    ),
                )
                if(it < 19) {
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
        }
    }
}