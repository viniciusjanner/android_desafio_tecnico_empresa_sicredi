package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.domain.model.EventCheckinSend
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.feature.checkin.EventCheckinUseCase
import com.viniciusjanner.desafio.sicredi.util.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventCheckinViewModel @Inject constructor(
    private val eventCheckinUseCase: EventCheckinUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val eventCheckinResponse: EventCheckinResponse) : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class SendCheckinEvent(val eventCheckinSend: EventCheckinSend) : Action()
    }

    private val _action = MutableLiveData<Action>()

    val state: LiveData<UiState> = _action
        .switchMap {
            liveData(coroutinesDispatchers.main()) {
                when (it) {
                    is Action.SendCheckinEvent -> {
                        eventCheckinUseCase.invoke(
                            EventCheckinUseCase.GetEventParam(it.eventCheckinSend)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                if (it.code == 200) {
                                    emit(UiState.Success(it))
                                } else {
                                    emit(UiState.Error)
                                }
                            },
                            error = {
                                emit(UiState.Error)
                            },
                        )
                    }
                }
            }
        }

    fun actionSendCheckin(checkin: EventCheckinSend) {
        _action.value = Action.SendCheckinEvent(checkin)
    }
}
