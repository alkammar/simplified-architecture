package com.plume.simplified.infra

import android.content.Context
import android.util.AttributeSet
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
            if (state != null) {
                onStateUpdated(state)
            }
        }
    }

    abstract fun stateConfiguration(): REQUEST

    protected abstract fun onStateUpdated(state: VIEW_STATE)
}