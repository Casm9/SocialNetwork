package com.casm.socialnetwork.feature_profile.domain.model

data class UpdateProfileData(
    val username: String,
    val bio: String,
    val gitHubUrl: String,
    val instagramUrl: String,
    val linkedinUrl: String,
    val skills: List<Skill>
)
