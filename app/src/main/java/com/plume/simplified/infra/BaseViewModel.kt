package com.plume.simplified.infra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plume.domain.infra.StateUseCase
import kotlinx.coroutines.Job
import java.lang.IllegalArgumentException

abstract class BaseViewModel<REQUEST, VIEW_STATE> : ViewModel() {
    internal val viewState = MutableLiveData<VIEW_STATE>().apply { value = initialState }
    abstract val stateUseCase: StateUseCase<REQUEST, VIEW_STATE>
    abstract val initialState: VIEW_STATE

    private var job: Job? = null

    fun onStart(request: REQUEST) {
        restart(request)
    }

    open fun onStateError(throwable: Throwable, request: REQUEST) {
        if (throwable is IllegalArgumentException) {
            println("We caught a state error $throwable")
            restart(request)
        }
    }

    fun onStop() {
        job?.cancel()
    }

    private fun restart(request: REQUEST) {
        job?.cancel()
        job = stateUseCase.execute(
            request = request,
            onState = { state -> viewState.value = state },
            onError = { throwable -> onStateError(throwable, request) }
        )
    }
}