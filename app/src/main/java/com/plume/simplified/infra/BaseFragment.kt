package com.plume.simplified.infra

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import javax.inject.Inject

abstract class BaseFragment<REQUEST, VIEW_STATE> : Fragment() {
    abstract val layoutId: Int
    abstract val viewModel: BaseViewModel<REQUEST, VIEW_STATE>

    @Inject
    lateinit var defaultErrorBehaviour: DefaultErrorBehavior

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            state?.let { onStateUpdated(state) }
        }

        viewModel.observeError(
            context = requireContext(),
            lifecycleOwner = viewLifecycleOwner,
            onError = ::onError
        )

        viewModel.onStart(stateConfiguration())

        return inflater.inflate(layoutId, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.onStop()
    }

    abstract fun stateConfiguration(): REQUEST

    protected abstract fun onStateUpdated(state: VIEW_STATE)

    protected open fun onError(context: Context, throwable: Throwable) {
        defaultErrorBehaviour(context, throwable)
    }

    fun requireSupportActivity() = requireActivity() as AppCompatActivity
}