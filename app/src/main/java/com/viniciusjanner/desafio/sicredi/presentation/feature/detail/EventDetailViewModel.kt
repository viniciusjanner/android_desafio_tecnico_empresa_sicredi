package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.feature.detail.EventDetailUseCase
import com.viniciusjanner.desafio.sicredi.util.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val eventDetailUseCase: EventDetailUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val event: Event) : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class GetEvent(val eventId: String) : Action()
    }

    private val _action = MutableLiveData<Action>()

    val state: LiveData<UiState> = _action
        .switchMap {
            liveData(coroutinesDispatchers.main()) {
                when (it) {
                    is Action.GetEvent -> {
                        eventDetailUseCase.invoke(
                            EventDetailUseCase.GetEventParam(it.eventId)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = { event ->
                                emit(UiState.Success(event))
                            },
                            error = {
                                emit(UiState.Error)
                            },
                        )
                    }
                }
            }
        }

    fun actionGetEvent(eventId: String) {
        _action.value = Action.GetEvent(eventId)
    }

    //
    // O valor de key deve ser igual ao name do argument contido no nav_graph.xml
    //
    private val argsKey = "eventDetailArgs"

    fun setSavedStateHandle(args: EventDetailArgs) {
        savedStateHandle[argsKey] = args
    }

    init {
        val args = savedStateHandle.get<EventDetailArgs>(argsKey)
        args?.eventId?.let { id ->
            actionGetEvent(id)
        }
    }
}
