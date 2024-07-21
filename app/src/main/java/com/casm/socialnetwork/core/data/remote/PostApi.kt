package com.casm.socialnetwork.core.data.remote

import com.casm.socialnetwork.core.data.dto.response.BasicApiResponse
import com.casm.socialnetwork.core.domain.models.Comment
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.feature_post.data.remote.dto.CommentDto
import com.casm.socialnetwork.feature_post.data.remote.request.CreatePostRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PostApi {

    @GET("/api/post/get")
    suspend fun getPostsForFollows(
       @Query("page") page: Int,
       @Query("pageSize") pageSize: Int
    ): List<Post>


    @GET("/api/user/posts")
    suspend fun getPostsForProfile(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>

    @Multipart
    @POST("/api/post/create")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ): BasicApiResponse<Unit>

    @GET("/api/post/details")
    suspend fun getPostDetails(
        @Query("postId") postId: String
    ): BasicApiResponse<Post>

    @GET("/api/comment/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId: String
    ): List<CommentDto>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}