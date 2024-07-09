package com.casm.socialnetwork.core.presentation.util


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.ui.platform.LocalContext
import com.casm.socialnetwork.core.domain.util.getFileName
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCrop.RESULT_ERROR
import java.io.File
import java.util.UUID

class CropActivityResultContract : ActivityResultContract<Uri, Uri?>() {
    override fun createIntent(context: Context, input: Uri): Intent {
        return UCrop.of(
            input,
            Uri.fromFile(
                File(
                    context.cacheDir,
                    context.contentResolver.getFileName(input)
                )
            )
        )
            .withAspectRatio(16f, 9f)
            .getIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode == RESULT_ERROR) {
            val error = UCrop.getError(intent!!)
            error?.printStackTrace()
        }
        return UCrop.getOutput(intent ?: return null)

    }
}