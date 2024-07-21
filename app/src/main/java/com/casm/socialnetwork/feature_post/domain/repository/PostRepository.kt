package com.casm.socialnetwork.feature_post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.casm.socialnetwork.core.domain.models.Comment
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import java.io.File


interface PostRepository {
    val posts: Flow<PagingData<Post>>

    suspend fun createPost(description: String, imageUri: Uri): SimpleResource

    suspend fun getPostDetails(postId: String): Resource<Post>

    suspend fun getCommentsForPost(postId: String): Resource<List<Comment>>
}