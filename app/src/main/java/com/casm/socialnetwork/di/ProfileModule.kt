package com.casm.socialnetwork.di

import com.casm.socialnetwork.feature_profile.data.remote.ProfileApi
import com.casm.socialnetwork.feature_profile.data.repository.ProfileRepositoryImpl
import com.casm.socialnetwork.feature_profile.domain.repository.ProfileRepository
import com.casm.socialnetwork.feature_profile.domain.use_case.GetProfileUseCase
import com.casm.socialnetwork.feature_profile.domain.use_case.GetSkillsUseCase
import com.casm.socialnetwork.feature_profile.domain.use_case.ProfileUseCases
import com.casm.socialnetwork.feature_profile.domain.use_case.SetSkillSelectedUseCase
import com.casm.socialnetwork.feature_profile.domain.use_case.UpdateProfileUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi{
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, gson: Gson): ProfileRepository {
        return ProfileRepositoryImpl(api, gson)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            getSkills = GetSkillsUseCase(repository),
            updateProfile = UpdateProfileUseCase(repository),
            setSkillSelected = SetSkillSelectedUseCase()
        )
    }
}