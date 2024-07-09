package com.casm.socialnetwork.feature_auth.data.repository


import android.content.SharedPreferences
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.util.Constants
import com.casm.socialnetwork.feature_auth.domain.repository.AuthRepository
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_auth.data.remote.request.CreateAccountRequest
import com.casm.socialnetwork.feature_auth.data.remote.request.LoginRequest
import com.casm.socialnetwork.feature_auth.data.remote.AuthApi
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        val request = CreateAccountRequest(email, username, password)
        return try {
            val response = api.register(request)
            if (response.successful) {
                Resource.Success(Unit)
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

    override suspend fun login(email: String, password: String): SimpleResource {
        val request = LoginRequest(email, password)
        return try {
            val response = api.login(request)
            if (response.successful) {
                response.data?.token?.let { token ->
                    sharedPreferences.edit().putString(Constants.KEY_JWT_TOKEN, token).apply()
                }
                Resource.Success(Unit)
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

    override suspend fun authenticate(): SimpleResource {
        return try {
            api.authenticate()
            Resource.Success(Unit)
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