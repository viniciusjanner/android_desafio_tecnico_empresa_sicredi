package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.viniciusjanner.desafio.core.usecase.EventDetailUseCase
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.testing.MainCoroutineRule
import com.viniciusjanner.desafio.testing.model.EventFactory
import com.viniciusjanner.desafio.testing.model.EventId
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
class EventDetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var eventDetailUseCase: EventDetailUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<EventDetailViewModel.UiState>

    private val id1 = EventId.Id1
    private val event = EventFactory().getEvent(id1)

    private lateinit var eventDetailViewModel: EventDetailViewModel

    @Before
    fun setUp() {
        eventDetailViewModel = EventDetailViewModel(
            eventDetailUseCase,
            mainCoroutineRule.testDispatcherProvider,
        ).apply {
            state.observeForever(uiStateObserver)
        }
    }

    @Test
    fun `should notify uiStateObserver with Success from UiState when get event returns success`() =
    //
    // deve notificar uiStateObserver com Success de UiState quando obter event retornando sucesso
        //
        runTest {
            // Arrange
            whenever(eventDetailUseCase.invoke(any())).thenReturn(flowOf(ResultStatus.Success(event)))

            // Act
            eventDetailViewModel.actionLoadEvent(event.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<EventDetailViewModel.UiState.Success>())

            val uiStateSuccess = eventDetailViewModel.state.value as EventDetailViewModel.UiState.Success
            val eventSuccess = uiStateSuccess.event

            Assert.assertEquals(event, eventSuccess)
        }

    @Test
    fun `should notify uiStateObserver with Error from UiState when get event returns an exception`() =
    //
    // deve notificar uiStateObserver com Error de UiState quando obter event retornando uma exceção
    //
        //
        runTest {
            // Arrange
            whenever(eventDetailUseCase.invoke(any())).thenReturn(flowOf(ResultStatus.Error(Throwable())))

            // Act
            eventDetailViewModel.actionLoadEvent(event.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<EventDetailViewModel.UiState.Error>())
        }
}
