package com.plume.simplified.devicelist

import com.plume.domain.devicelist.DeviceList
import com.plume.domain.devicelist.GetDeviceListUseCase
import com.plume.simplified.infra.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    getDeviceListUseCase: GetDeviceListUseCase
) : BaseViewModel<Unit, DeviceList>() {
    override val initialState = DeviceList.Unknown
    override val stateUseCase = getDeviceListUseCase
}