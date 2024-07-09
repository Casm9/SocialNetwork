package com.casm.socialnetwork.feature_post.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.casm.socialnetwork.R
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.domain.util.getFileName
import com.casm.socialnetwork.core.util.Constants
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource
import com.casm.socialnetwork.core.util.UIText
import com.casm.socialnetwork.feature_auth.data.remote.request.CreateAccountRequest
import com.casm.socialnetwork.feature_auth.data.paging.PostSource
import com.casm.socialnetwork.feature_post.data.remote.PostApi
import com.casm.socialnetwork.feature_post.data.remote.request.CreatePostRequest
import com.casm.socialnetwork.feature_post.domain.repository.PostRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson
) : PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE_POSTS)) {
            PostSource(api)
        }.flow

    override suspend fun createPost(
        description: String,
        imageUri: Uri
    ): SimpleResource {
        val request = CreatePostRequest(description)
        val file = imageUri.toFile()

        return try {
            val response = api.createPost(
                postData = MultipartBody.Part.createFormData(
                    "post_data", gson.toJson(request)
                ),
                postImage = MultipartBody.Part.createFormData(
                    name = "post_image",
                    filename = file.name,
                    body = file.asRequestBody()
                )

            )
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
}