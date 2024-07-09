package com.casm.socialnetwork.feature_auth.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.casm.socialnetwork.core.domain.models.Post
import com.casm.socialnetwork.core.util.Constants
import com.casm.socialnetwork.feature_post.data.remote.PostApi
import retrofit2.HttpException
import java.io.IOException

class PostSource(
    private val api: PostApi
): PagingSource<Int, Post>() {
    private var currentPage = 0

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPage = params.key ?: currentPage
            val posts = api.getPostsForFollows(
                page = nextPage,
                pageSize = Constants.PAGE_SIZE_POSTS
            )
            LoadResult.Page(
                data = posts,
                prevKey = if(nextPage == 0) null else nextPage - 1,
                nextKey = if(posts.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return  LoadResult.Error(exception)
        }

    }
}