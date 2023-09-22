package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.viniciusjanner.desafio.core.domain.model.EventCheckInSend
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

    sealed class CheckinState {
        data object Loading : CheckinState()
        data class Success(val code: Int) : CheckinState()
        data object Error : CheckinState()
    }

    sealed class ActionCheckin {
        data class Send(val checkin: EventCheckInSend) : ActionCheckin()
    }

    private val actionCheckin = MutableLiveData<ActionCheckin>()

    val stateCheckin: LiveData<CheckinState> = actionCheckin
        .switchMap {
            liveData(coroutinesDispatchers.main()) {
                when (it) {
                    is ActionCheckin.Send -> {
                        eventCheckinUseCase.invoke(
                            EventCheckinUseCase.GetEventParam(it.checkin)
                        ).watchStatus(
                            loading = {
                                emit(CheckinState.Loading)
                            },
                            success = {
                                if (it.code == 200) {
                                    emit(CheckinState.Success(it.code))
                                } else {
                                    emit(CheckinState.Error)
                                }
                            },
                            error = {
                                emit(CheckinState.Error)
                            },
                        )
                    }
                }
            }
        }

    fun actionSendCheckin(checkin: EventCheckInSend) {
        actionCheckin.value = ActionCheckin.Send(checkin)
    }
}
