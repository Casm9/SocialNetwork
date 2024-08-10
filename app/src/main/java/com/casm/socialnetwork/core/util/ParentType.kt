package com.casm.socialnetwork.core.util

sealed class ParentType(val type: Int) {
    data object Post: ParentType(0)
    data object Comment: ParentType(1)
}
