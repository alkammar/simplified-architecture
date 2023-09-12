package com.plume.simplified.devicedetails

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.plume.domain.devicedetails.DeviceDetails
import com.plume.domain.devicedetails.DeviceDetails.Connected
import com.plume.domain.devicedetails.DeviceDetails.NoDevice
import com.plume.domain.devicedetails.DeviceDetails.NotConnected
import com.plume.domain.devicedetails.DeviceDetails.Unknown
import com.plume.entity.exception.RemoveMainDeviceException
import com.plume.simplified.R
import com.plume.simplified.infra.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceDetailsFragment : BaseFragment<String, DeviceDetails>() {

    override val layoutId = R.layout.fragment_device_details

    override val viewModel: DeviceDetailsViewModel by viewModels()

    private val deviceLabel: TextView get() = requireView().findViewById(R.id.device_details_device_label)
    private val nodeLabel: TextView get() = requireView().findViewById(R.id.device_details_node_label)
    private val deviceNodeConnection: View get() = requireView().findViewById(R.id.device_details_device_node_connection)
    private val removeDeviceButton: Button get() = requireView().findViewById(R.id.device_details_remove_device_button)

    private val args: DeviceDetailsFragmentArgs by navArgs()

    override fun stateConfiguration() = args.macAddress

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        removeDeviceButton.setOnClickListener { viewModel.onRemoveDeviceAction(args.macAddress) }
    }

    override fun onStateUpdated(state: DeviceDetails) {
        when (state) {
            Unknown -> {
                deviceLabel.text = "--"
                deviceNodeConnection.isVisible = false
                removeDeviceButton.isEnabled = false
            }

            NoDevice -> {
                deviceLabel.text = getString(R.string.device_details_no_device_message)
                nodeLabel.text = ""
                deviceNodeConnection.isVisible = false
                removeDeviceButton.isEnabled = false
            }

            is NotConnected -> {
                deviceLabel.text = state.device.name
                nodeLabel.text = getString(R.string.device_details_not_connected_message)
                deviceNodeConnection.isVisible = false
                requireSupportActivity().supportActionBar?.title = state.device.name
            }

            is Connected -> {
                deviceLabel.text = state.device.name
                nodeLabel.text = state.node.name
                deviceNodeConnection.isVisible = true
                requireSupportActivity().supportActionBar?.title = state.device.name
            }
        }
    }

    override fun onError(context: Context, throwable: Throwable) {
        when (throwable) {
            is RemoveMainDeviceException -> {
                AlertDialog.Builder(context)
                    .setTitle("Removing main device!")
                    .setMessage("You cannot remove the main device")
                    .setPositiveButton("Ok") { _, _ -> }
                    .show()
            }
            else -> super.onError(context, throwable)
        }
    }
}