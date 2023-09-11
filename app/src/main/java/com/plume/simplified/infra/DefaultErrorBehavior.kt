package com.plume.simplified.infra

import android.content.Context
import androidx.appcompat.app.AlertDialog

class DefaultErrorBehavior : (Context, Throwable) -> Unit {
    override fun invoke(context: Context, throwable: Throwable) {
        AlertDialog.Builder(context)
            .setTitle("What happened?")
            .setMessage("We need to talk\n $throwable")
            .setPositiveButton("Ok") { _, _ -> }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }
}