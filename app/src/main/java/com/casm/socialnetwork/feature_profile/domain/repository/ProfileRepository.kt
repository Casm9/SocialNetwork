package com.casm.socialnetwork.feature_profile.domain.repository

import android.net.Uri
import androidx.compose.foundation.pager.PageSize
import androidx.paging.PagingData
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.domain.models.UserItem
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.feature_profile.domain.model.Profile
import com.casm.socialnetwork.feature_profile.domain.model.Skill
import com.casm.socialnetwork.feature_profile.domain.model.UpdateProfileData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface ProfileRepository {

    fun getPostsPaged(userId: String): Flow<PagingData<Post>>

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>

    suspend fun searchUser(query: String): Resource<List<UserItem>>

    suspend fun followUser(userId: String): SimpleResource

    suspend fun unfollowUser(userId: String): SimpleResource
}