package com.casm.socialnetwork.feature_profile.domain.repository

import android.net.Uri
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.feature_profile.domain.model.Profile
import com.casm.socialnetwork.feature_profile.domain.model.Skill
import com.casm.socialnetwork.feature_profile.domain.model.UpdateProfileData

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>
}