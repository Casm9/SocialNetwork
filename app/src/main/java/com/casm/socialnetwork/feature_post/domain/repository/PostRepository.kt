package com.casm.socialnetwork.feature_post.domain.repository

import android.net.Uri
import com.casm.socialnetwork.core.domain.models.Comment
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.domain.models.UserItem
import com.casm.socialnetwork.core.util.Resource
import com.casm.socialnetwork.core.util.SimpleResource

interface PostRepository {
    suspend fun createPost(description: String, imageUri: Uri): SimpleResource
    suspend fun getPostDetails(postId: String): Resource<Post>
    suspend fun getCommentsForPost(postId: String): Resource<List<Comment>>
    suspend fun createComment(postId: String, comment: String): SimpleResource
    suspend fun likeParent(parentId: String, parentType: Int): SimpleResource
    suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource
    suspend fun getLikesForParent(parentId: String): Resource<List<UserItem>>
    suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<Post>>
    suspend fun deletePost(postId: String): SimpleResource
}