package com.casm.socialnetwork.feature_profile.data.repository

import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_profile.data.remote.ProfileApi
import com.casm.socialnetwork.feature_profile.domain.model.Profile
import com.casm.socialnetwork.feature_profile.domain.repository.ProfileRepository
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val api: ProfileApi
): ProfileRepository {
    override suspend fun getProfile(userId: String): Resource<Profile> {
        return try {
            val response = api.getProfile(userId)
            if (response.successful) {
                Resource.Success(response.data?.toProfile())
            } else {
                response.message?.let { msg ->
                    Resource.Error(UIText.DynamicString(msg))
                } ?: Resource.Error(UIText.StringResource(R.string.error_unknown))

            }

        } catch (e: IOException) {
            Resource.Error(
                uiText = UIText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UIText.StringResource(R.string.error_someting_went_wrong)
            )
        }
    }
}