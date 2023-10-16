package com.viniciusjanner.desafio.sicredi.presentation.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.feature.list.EventListUseCase
import com.viniciusjanner.desafio.testing.MainCoroutineRule
import com.viniciusjanner.desafio.testing.core.domain.model.EventFactory
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EventListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var useCase: EventListUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<EventListViewModel.UiState>

    private val event1 = EventFactory().create(EventFactory.EventFake.Event1)
    private val event2 = EventFactory().create(EventFactory.EventFake.Event2)
    private val eventList = listOf(event1, event2)

    private lateinit var viewModel: EventListViewModel

    @Before
    fun setUp() {
        viewModel = EventListViewModel(
            useCase,
            mainCoroutineRule.coroutinesDispatchers,
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
            whenever(useCase.invoke(any())).thenReturn(flowOf(ResultStatus.Success(eventList)))

            // Act
            viewModel.actionGetEvents()

            // Assert
            verify(uiStateObserver).onChanged(isA<EventListViewModel.UiState.Success>())

            val uiStateSuccess = viewModel.state.value as EventListViewModel.UiState.Success
            val eventsSuccess = uiStateSuccess.events

            Assert.assertEquals(eventList, eventsSuccess)
        }

    @Test
    fun `should notify uiStateObserver with Error from UiState when get event returns an exception`() =
        //
        // deve notificar uiStateObserver com Error de UiState quando obter event retornando uma exceção
        //
        runTest {
            // Arrange
            whenever(useCase.invoke(any())).thenReturn(flowOf(ResultStatus.Error(Throwable())))

            // Act
            viewModel.actionGetEvents()

            // Assert
            verify(uiStateObserver).onChanged(isA<EventListViewModel.UiState.Error>())
        }
}
