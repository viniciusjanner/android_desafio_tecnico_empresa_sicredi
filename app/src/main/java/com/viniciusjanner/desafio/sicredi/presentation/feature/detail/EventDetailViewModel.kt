package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.EventDetailUseCase
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.sicredi.util.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val eventDetailUseCase: EventDetailUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val event: Event) : UiState()
        data object Error : UiState()
    }

    sealed class Action {
        data class Load(val eventId: String) : Action()
    }

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action
        .switchMap {
            liveData(coroutinesDispatchers.main()) {
                when (it) {
                    is Action.Load -> {
                        eventDetailUseCase.invoke(
                            EventDetailUseCase.GetEventParam(it.eventId)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                emit(UiState.Success(it))
                            },
                            error = {
                                emit(UiState.Error)
                            },
                        )
                    }
                }
            }
        }

    fun actionLoadEvent(eventId: String) {
        action.value = Action.Load(eventId)
    }
}
