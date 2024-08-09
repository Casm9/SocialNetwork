package com.casm.socialnetwork.core.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import com.casm.socialnetwork.R

fun Context.showKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(null, InputMethodManager.SHOW_FORCED)
}

fun Context.sendSharePostIntent(postId: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.share_intent_text, "https://casm.com/$postId")
        )
        type = "text/plain"
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(Intent.createChooser(intent, "Select an app"))
    }
}

fun Context.openUrlInBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }

    startActivity(Intent.createChooser(intent, "Select an app"))

}