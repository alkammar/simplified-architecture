package com.plume.simplified.devicedetails

import com.plume.domain.devicedetails.DeviceDetails
import com.plume.domain.devicedetails.GetDeviceDetailsUseCase
import com.plume.domain.devicedetails.RemoveDeviceUseCase
import com.plume.domain.infra.UseCaseExecutor
import com.plume.simplified.infra.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    getDeviceDetailsUseCase: GetDeviceDetailsUseCase,
    private val removeDeviceUseCase: RemoveDeviceUseCase,
    private val useCaseExecutor: UseCaseExecutor
) : BaseViewModel<String, DeviceDetails>(useCaseExecutor) {
    override val initialState = DeviceDetails.Unknown
    override val stateUseCase = getDeviceDetailsUseCase

    fun onRemoveDeviceAction(macAddress: String) {
        useCaseExecutor.execute(removeDeviceUseCase, macAddress, ::notifyError)
    }
}