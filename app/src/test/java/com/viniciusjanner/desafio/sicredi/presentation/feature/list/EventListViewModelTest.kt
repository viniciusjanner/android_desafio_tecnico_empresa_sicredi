package com.viniciusjanner.desafio.sicredi.presentation.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.EventListUseCase
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.testing.MainCoroutineRule
import com.viniciusjanner.desafio.testing.model.EventsFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@Suppress("MaxLineLength")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EventListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var eventListUseCase: EventListUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<EventListViewModel.UiState>

    private val events: List<Event> = EventsFactory().getEvents()

    private lateinit var eventListViewModel: EventListViewModel

    @Before
    fun setUp() {
        eventListViewModel = EventListViewModel(
            eventListUseCase,
            mainCoroutineRule.testDispatcherProvider,
        ).apply {
            state.observeForever(uiStateObserver)
        }
    }

    @Test
    fun `should notify uiStateObserver with Success from UiState when get event list returns success`() =
    //
    // deve notificar uiStateObserver com Success de UiState quando obter event list retornando sucesso
        //
        runTest {
            // Arrange
            whenever(eventListUseCase.invoke(any())).thenReturn(flowOf(ResultStatus.Success(events)))

            // Act
            eventListViewModel.actionGetEvents()

            // Assert
            verify(uiStateObserver).onChanged(isA<EventListViewModel.UiState.Success>())

            val uiStateSuccess = eventListViewModel.state.value as EventListViewModel.UiState.Success
            val eventsSuccess = uiStateSuccess.events

            Assert.assertEquals(events, eventsSuccess)
        }

    @Test
    fun `should notify uiStateObserver with Error from UiState when get event returns an exception`() =
    //
    // deve notificar uiStateObserver com Error de UiState quando obter event retornando uma exceção
    //
        //
        runTest {
            // Arrange
            whenever(eventListUseCase.invoke(any())).thenReturn(flowOf(ResultStatus.Error(Throwable())))

            // Act
            eventListViewModel.actionGetEvents()

            // Assert
            verify(uiStateObserver).onChanged(isA<EventListViewModel.UiState.Error>())
        }
}
