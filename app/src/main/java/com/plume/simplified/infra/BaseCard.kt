package com.plume.simplified.infra

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.findViewTreeLifecycleOwner

abstract class BaseCard<REQUEST, VIEW_STATE>(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int
) : CardView(context, attrs, defStyleAttr) {

    abstract val viewModel: BaseViewModel<REQUEST, VIEW_STATE>

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        viewModel.onStart(stateConfiguration())
        viewModel.viewState.observe(findViewTreeLifecycleOwner()!!) { state ->
            state?.let { onStateUpdated(it) }
        }

        viewModel.errorEvent.observe(findViewTreeLifecycleOwner()!!) { event ->
            event?.let {
                AlertDialog.Builder(context)
                    .setTitle("What happened?")
                    .setMessage("We need to talk")
                    .setPositiveButton("Ok") { _, _ -> }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
            }
        }
    }

    abstract fun stateConfiguration(): REQUEST

    protected abstract fun onStateUpdated(state: VIEW_STATE)
}