package com.plume.simplified.infra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plume.domain.infra.ActionUseCase
import com.plume.domain.infra.StateUseCase
import com.plume.domain.infra.UseCaseExecutor
import kotlinx.coroutines.Job

abstract class BaseViewModel<REQUEST, VIEW_STATE>(
    private val useCaseExecutor: UseCaseExecutor
) : ViewModel() {
    internal val viewState = MutableLiveData<VIEW_STATE>().apply { value = initialState }
    internal val errorEvent = SingleLiveEvent<Throwable>()
    abstract val stateUseCase: StateUseCase<REQUEST, VIEW_STATE>
    abstract val initialState: VIEW_STATE

    private var job: Job? = null

    fun onStart(request: REQUEST) {
        restart(request)
    }

    open fun onStateError(throwable: Throwable, request: REQUEST) {
        errorEvent.value = throwable
    }

    open fun onActionError(throwable: Throwable) {
        errorEvent.value = throwable
    }

    fun onStop() {
        job?.cancel()
    }

    private fun restart(request: REQUEST) {
        job?.cancel()
        useCaseExecutor.execute(
            stateUseCase = stateUseCase,
            request = request,
            onState = { state -> viewState.value = state },
            onError = { throwable -> onStateError(throwable, request) }
        )
    }

    fun <REQ> ActionUseCase<REQ>.execute(
        request: REQ
    ) {
        useCaseExecutor.execute(
            actionUseCase = this,
            request = request,
            onError = ::onActionError
        )
    }
}