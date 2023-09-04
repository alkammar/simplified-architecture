package com.plume.simplified.devicelist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plume.domain.devicelist.DeviceList
import com.plume.domain.devicelist.DeviceList.HasDevices
import com.plume.domain.devicelist.DeviceList.NoDevices
import com.plume.domain.devicelist.DeviceList.Unknown
import com.plume.simplified.R
import com.plume.simplified.infra.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceListFragment : BaseFragment<Unit, DeviceList>() {

    override val layoutId = R.layout.fragment_device_list

    override val viewModel: DeviceListViewModel by viewModels()

    private val empty: TextView get() = requireView().findViewById(R.id.device_list_empty_label)
    private val deviceList: RecyclerView get() = requireView().findViewById(R.id.device_list_device_list)
    private val deviceAdapter = DeviceAdapter()

    override fun stateConfiguration() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceList.layoutManager = LinearLayoutManager(context)
        deviceList.adapter = deviceAdapter
    }

    override fun onStateUpdated(state: DeviceList) {
        when (state) {
            Unknown -> {
                empty.isVisible = false
            }

            NoDevices -> {
                empty.isVisible = true
                deviceAdapter.data = emptyList()
            }

            is HasDevices -> {
                empty.isVisible = false
                deviceAdapter.data = state.devices
            }
        }
    }
}