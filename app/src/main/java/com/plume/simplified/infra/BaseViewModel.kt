package com.plume.simplified.infra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plume.domain.infra.StateUseCase
import kotlinx.coroutines.Job

abstract class BaseViewModel<REQUEST, VIEW_STATE> : ViewModel() {
    internal val viewState = MutableLiveData<VIEW_STATE>().apply { value = initialState }
    abstract val stateUseCase: StateUseCase<REQUEST, VIEW_STATE>
    abstract val initialState: VIEW_STATE

    private lateinit var job: Job

    fun onStart(request: REQUEST) {
        job = stateUseCase.execute(request) { viewState.value = it }
    }

    fun onStop() {
        job.cancel()
    }
}