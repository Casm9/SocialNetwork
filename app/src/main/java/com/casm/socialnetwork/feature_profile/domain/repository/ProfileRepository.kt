package com.casm.socialnetwork.feature_profile.domain.repository

import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_profile.domain.model.Profile

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>
}