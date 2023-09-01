package com.plume.simplified.infra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment<REQUEST, VIEW_STATE> : Fragment() {
    abstract val layoutId: Int
    abstract val viewModel: BaseViewModel<REQUEST, VIEW_STATE>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            state?.let { onStateUpdated(state) }
        }
    }

    protected abstract fun onStateUpdated(state: VIEW_STATE)

    fun requireSupportActivity() = requireActivity() as AppCompatActivity
}