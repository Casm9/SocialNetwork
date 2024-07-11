package com.casm.socialnetwork.feature_profile.domain.use_case

import android.net.Uri
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_profile.domain.model.UpdateProfileData
import com.casm.socialnetwork.feature_profile.domain.repository.ProfileRepository

class UpdateProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerUri: Uri?
    ): SimpleResource {
        if(updateProfileData.username.isBlank()) {
            return Resource.Error(
                uiText = UIText.StringResource(R.string.error_username_empty)
            )
        }
         return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerUri
        )
    }
}