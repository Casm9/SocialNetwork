package com.casm.socialnetwork.core.presentation.util

import android.content.Context
import com.casm.socialnetwork.core.util.UIText

fun UIText.asString(context: Context): String {
    return when(this) {
        is UIText.DynamicString -> this.value
        is UIText.StringResource -> context.getString(this.id)
    }
}