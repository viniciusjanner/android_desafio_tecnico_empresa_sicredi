package com.viniciusjanner.desafio.sicredi.presentation.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.viniciusjanner.desafio.core.usecase.EventsUseCase
import com.viniciusjanner.desafio.core.usecase.base.CoroutinesDispatchers
import com.viniciusjanner.desafio.sicredi.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventsUseCase: EventsUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action
        .switchMap { action ->
            liveData(coroutinesDispatchers.main()) {
                when (action) {
                    is Action.GetEvents -> {
                        eventsUseCase.invoke().watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                val items = it.map { event ->
                                    EventItem(
                                        event.id,
                                        event.people,
                                        event.date,
                                        event.description,
                                        event.image,
                                        event.longitude,
                                        event.latitude,
                                        event.price,
                                        event.title,
                                    )
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
        action.value = Action.GetEvents
    }

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val eventsList: List<EventItem>) : UiState()
        data object Empty : UiState()
        data object Error : UiState()
    }

    sealed class Action {
        data object GetEvents : Action()
    }
}
