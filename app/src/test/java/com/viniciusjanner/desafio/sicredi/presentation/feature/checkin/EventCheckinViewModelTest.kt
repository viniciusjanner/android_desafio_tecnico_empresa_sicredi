package com.viniciusjanner.desafio.sicredi.presentation.feature.checkin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.viniciusjanner.desafio.core.usecase.EventCheckinUseCase
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.testing.MainCoroutineRule
import com.viniciusjanner.desafio.testing.model.EventCheckinFactory
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
class EventCheckinViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var eventCheckinUseCase: EventCheckinUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<EventCheckinViewModel.UiState>

    private val checkinSend = EventCheckinFactory().checkinSend()
    private val checkinResponse = EventCheckinFactory().checkinResponse()

    private lateinit var eventCheckinViewModel: EventCheckinViewModel

    @Before
    fun setUp() {
        eventCheckinViewModel = EventCheckinViewModel(
            eventCheckinUseCase,
            mainCoroutineRule.testDispatcherProvider,
        ).apply {
            stateCheckin.observeForever(uiStateObserver)
        }
    }

    @Test
    fun `should notify uiStateObserver with Success from UiState when get event returns success`() =
    //
    // deve notificar uiStateObserver com Success de UiState quando obter event retornando sucesso
        //
        runTest {
            // Arrange
            whenever(eventCheckinUseCase.invoke(any())).thenReturn(flowOf(ResultStatus.Success(checkinResponse)))

            // Act
            eventCheckinViewModel.actionSendCheckin(checkinSend)

            // Assert
            verify(uiStateObserver).onChanged(isA<EventCheckinViewModel.UiState.Success>())

            val checkinStateSuccess = eventCheckinViewModel.stateCheckin.value as EventCheckinViewModel.UiState.Success
            val checkinResponseSuccess = checkinStateSuccess.checkinResponse

            Assert.assertEquals(checkinResponse, checkinResponseSuccess)
        }

    @Test
    fun `should notify uiStateObserver with Error from UiState when get event returns an exception`() =
        //
        // deve notificar uiStateObserver com Error de UiState quando obter event retornando uma exceção
        //
        //
        runTest {
            // Arrange
            whenever(eventCheckinUseCase.invoke(any())).thenReturn(flowOf(ResultStatus.Error(Throwable())))

            // Act
            eventCheckinViewModel.actionSendCheckin(checkinSend)

            // Assert
            verify(uiStateObserver).onChanged(isA<EventCheckinViewModel.UiState.Error>())
        }
}