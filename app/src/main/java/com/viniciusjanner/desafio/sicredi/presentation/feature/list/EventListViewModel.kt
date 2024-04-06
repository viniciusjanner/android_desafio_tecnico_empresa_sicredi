package com.viniciusjanner.desafio.sicredi.presentation.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.domain.model.toEventModel
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.core.usecase.feature.list.EventListUseCase
import com.viniciusjanner.desafio.sicredi.util.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventListUseCase: EventListUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val events: List<Event>) : UiState()
        object Empty : UiState()
        object Error : UiState()
    }

    sealed class Action {
        object GetEvents : Action()
    }

    private val _action = MutableLiveData<Action>(Action.GetEvents)

    val state: LiveData<UiState> = _action
        .switchMap { action ->
            liveData(coroutinesDispatchers.main()) {
                when (action) {
                    is Action.GetEvents -> {
                        eventListUseCase.invoke().watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                val items = it.map { event ->
                                    event.toEventModel()
                                }
                                val uiState = if (it.isEmpty()) {
                                    UiState.Empty
                                } else {
                                    UiState.Success(items)
                                }
                                emit(uiState)
                            },
                            error = {
                                emit(UiState.Error)
                            },
                        )
                    }
                }
            }
        }

    fun actionGetEvents() {
        _action.value = Action.GetEvents
    }
}
