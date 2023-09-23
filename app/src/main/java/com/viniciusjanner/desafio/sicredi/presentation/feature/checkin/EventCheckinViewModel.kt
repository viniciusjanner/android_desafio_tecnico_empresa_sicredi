package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.viniciusjanner.desafio.core.domain.model.EventCheckInSend
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.usecase.EventCheckinUseCase
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.sicredi.util.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventCheckinViewModel @Inject constructor(
    private val eventCheckinUseCase: EventCheckinUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val checkinResponse: EventCheckinResponse) : UiState()
        data object Error : UiState()
    }

    sealed class Action {
        data class Send(val checkinSend: EventCheckInSend) : Action()
    }

    private val actionCheckin = MutableLiveData<Action>()

    val stateCheckin: LiveData<UiState> = actionCheckin
        .switchMap {
            liveData(coroutinesDispatchers.main()) {
                when (it) {
                    is Action.Send -> {
                        eventCheckinUseCase.invoke(
                            EventCheckinUseCase.GetEventParam(it.checkinSend)
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

    fun actionSendCheckin(checkin: EventCheckInSend) {
        actionCheckin.value = Action.Send(checkin)
    }
}
