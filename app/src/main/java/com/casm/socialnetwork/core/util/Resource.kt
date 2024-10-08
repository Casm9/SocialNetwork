package com.casm.socialnetwork.core.util


typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val uiText: UIText? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(uiText: UIText, data: T? = null) : Resource<T>(data, uiText)
}