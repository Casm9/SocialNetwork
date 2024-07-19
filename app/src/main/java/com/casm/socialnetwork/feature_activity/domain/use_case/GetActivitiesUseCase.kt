package com.casm.socialnetwork.feature_activity.domain.use_case

import androidx.paging.PagingData
import com.casm.socialnetwork.core.domain.models.Activity
import com.casm.socialnetwork.feature_activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow

class GetActivitiesUseCase(
    private val repository: ActivityRepository
) {
    operator fun invoke(): Flow<PagingData<Activity>> {
        return repository.activities
    }
}