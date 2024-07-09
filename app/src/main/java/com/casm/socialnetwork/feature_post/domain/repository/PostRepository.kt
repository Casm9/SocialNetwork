package com.casm.socialnetwork.feature_post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import java.io.File


interface PostRepository {
    val posts: Flow<PagingData<Post>>

    suspend fun createPost(description: String, imageUri: Uri): SimpleResource
}