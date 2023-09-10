package com.plume.simplified.infra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            state?.let { onStateUpdated(state) }
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { event ->
            event?.let {
                AlertDialog.Builder(requireContext())
                    .setTitle("What happened?")
                    .setMessage("We need to talk")
                    .setPositiveButton("Ok") { _, _ -> }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
            }
        }

        viewModel.onStart(stateConfiguration())

        return inflater.inflate(layoutId, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.onStop()
    }

    abstract fun stateConfiguration(): REQUEST

    protected abstract fun onStateUpdated(state: VIEW_STATE)

    fun requireSupportActivity() = requireActivity() as AppCompatActivity
}