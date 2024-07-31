package com.casm.socialnetwork.feature_profile.domain.use_case

import android.net.Uri
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_profile.domain.model.UpdateProfileData
import com.casm.socialnetwork.core.domain.repository.ProfileRepository
import com.casm.socialnetwork.feature_profile.domain.util.ProfileConstants

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

        val isValidGithubUrl = updateProfileData.gitHubUrl.isBlank() || ProfileConstants.GITHUB_PROFILE_REGEX.matches(updateProfileData.gitHubUrl)
        if (!isValidGithubUrl){
            return Resource.Error(
                uiText = UIText.StringResource(R.string.error_invalid_github_url)
            )
        }

        val isValidInstagramUrl = updateProfileData.instagramUrl.isBlank() || ProfileConstants.INSTAGRAM_PROFILE_REGEX.matches(updateProfileData.instagramUrl)
        if (!isValidInstagramUrl){
            return Resource.Error(
                uiText = UIText.StringResource(R.string.error_invalid_instagram_url)
            )
        }

        val isValidLinkedInUrl = updateProfileData.linkedInUrl.isBlank() || ProfileConstants.LINKED_IN_PROFILE_REGEX.matches(updateProfileData.linkedInUrl)
        if (!isValidLinkedInUrl){
            return Resource.Error(
                uiText = UIText.StringResource(R.string.error_invalid_linkedin_url)
            )
        }

         return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerUri
        )
    }
}