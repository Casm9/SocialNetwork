package com.casm.socialnetwork.feature_profile.domain.use_case

import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.feature_profile.domain.model.Skill
import com.casm.socialnetwork.core.domain.repository.ProfileRepository

class GetSkillsUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Resource<List<Skill>> {
        return repository.getSkills()
    }
}