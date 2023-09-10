package com.plume.simplified.devicelist

import com.plume.domain.devicelist.DeviceList
import com.plume.domain.devicelist.GetDeviceListUseCase
import com.plume.domain.infra.UseCaseExecutor
import com.plume.simplified.infra.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    getDeviceListUseCase: GetDeviceListUseCase,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<Unit, DeviceList>(useCaseExecutor) {
    override val initialState = DeviceList.Unknown
    override val stateUseCase = getDeviceListUseCase
}