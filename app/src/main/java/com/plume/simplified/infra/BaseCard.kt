package com.plume.simplified.infra

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.lifecycle.findViewTreeLifecycleOwner
import javax.inject.Inject

abstract class BaseCard<REQUEST, VIEW_STATE>(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int
) : CardView(context, attrs, defStyleAttr) {

    abstract val viewModel: BaseViewModel<REQUEST, VIEW_STATE>

    @Inject
    lateinit var defaultErrorBehaviour: DefaultErrorBehavior

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        viewModel.onStart(stateConfiguration())
        viewModel.viewState.observe(findViewTreeLifecycleOwner()!!) { state ->
            state?.let { onStateUpdated(it) }
        }

        viewModel.observeError(
            context = context,
            lifecycleOwner = findViewTreeLifecycleOwner()!!,
            onError = { context, throwable -> onError(context, throwable) }
        )
    }

    abstract fun stateConfiguration(): REQUEST

    protected abstract fun onStateUpdated(state: VIEW_STATE)

    protected open fun onError(context: Context, throwable: Throwable) {
        defaultErrorBehaviour(context, throwable)
    }
}