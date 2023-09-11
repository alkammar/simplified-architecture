package com.plume.simplified.infra

import android.content.Context
import androidx.lifecycle.LifecycleOwner

fun <REQUEST, VIEW_STATE> BaseViewModel<REQUEST, VIEW_STATE>.observeError(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onError: (context: Context, throwable: Throwable) -> Unit
) {
    errorEvent.observe(lifecycleOwner) { throwable ->
        onError(context, throwable)
    }
}