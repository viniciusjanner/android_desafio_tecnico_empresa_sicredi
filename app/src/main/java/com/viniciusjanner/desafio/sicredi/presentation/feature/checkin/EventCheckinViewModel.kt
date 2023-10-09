package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    sealed class ErrorMessage {
        object Invalid : ErrorMessage()
        object Required : ErrorMessage()
        object None : ErrorMessage()
    }

    private val _errorName: MutableLiveData<ErrorMessage> = MutableLiveData()
    val errorName: LiveData<ErrorMessage> = _errorName

    private val _errorEmail: MutableLiveData<ErrorMessage> = MutableLiveData()
    val errorEmail: LiveData<ErrorMessage> = _errorEmail

    val enableButtonMediator = MediatorLiveData<Boolean>()
        .apply {
            fun update() {
                val isNameValid = errorName.value == ErrorMessage.None
                val isEmailValid = errorEmail.value == ErrorMessage.None
                value = isNameValid && isEmailValid
            }
            addSource(errorName) { update() }
            addSource(errorEmail) { update() }
        }

    fun isEnableButton(): Boolean = enableButtonMediator.value ?: false

    fun setName(text: String?) {
        text?.trim()?.let {
            _errorName.value =
                when {
                    it.trim().isEmpty() -> {
                        ErrorMessage.Required
                    }
                    it.length < 2 -> {
                        ErrorMessage.Invalid
                    }
                    else -> {
                        ErrorMessage.None
                    }
                }
        }
    }

    fun setEmail(text: String?) {
        text?.trim()?.let {
            _errorEmail.value =
                when {
                    it.isEmpty() -> {
                        ErrorMessage.Required
                    }
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() -> {
                        ErrorMessage.Invalid
                    }
                    else -> {
                        ErrorMessage.None
                    }
                }
        }
    }

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
