package com.casm.socialnetwork.core.util

interface Paginator<T> {

    suspend fun loadNextItems()
}