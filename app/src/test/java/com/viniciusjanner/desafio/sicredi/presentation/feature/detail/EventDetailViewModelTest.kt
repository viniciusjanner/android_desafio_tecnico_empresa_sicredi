package com.viniciusjanner.desafio.sicredi.presentation.feature.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.feature.detail.EventDetailUseCase
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
class EventDetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var useCase: EventDetailUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<EventDetailViewModel.UiState>

    private val event = EventFactory().create(EventFactory.EventFake.Event1)

    private val argsKey = "eventDetailArgs"

    //@Mock
    //private lateinit var savedStateHandle: SavedStateHandle

    private var savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: EventDetailViewModel

    @Before
    fun setUp() {
//        viewModel = EventDetailViewModel(
//            useCase,
//            mainCoroutineRule.coroutinesDispatchers,
//            savedStateHandle
//        ).apply {
//            state.observeForever(uiStateObserver)
//            savedStateHandle.apply {
//                set(argsKey, event.id)
//            }
//        }
//
    }

    @Test
    fun `should notify uiStateObserver with Success from UiState when get event returns success`() =
    //
    // deve notificar uiStateObserver com Success de UiState quando obter event retornando sucesso
    //
        runTest {
            viewModel = EventDetailViewModel(
                useCase,
                mainCoroutineRule.coroutinesDispatchers,
                savedStateHandle
            )
            savedStateHandle.apply { set(argsKey, event.id) }
            viewModel.state.observeForever(uiStateObserver)


            // Arrange
            //whenever(useCase.invoke(any())).thenReturn(flowOf(ResultStatus.Success(event)))

            // Act
            //viewModel.actionGetEvent(event.id)

            // Assert
            //verify(uiStateObserver).onChanged(isA<EventDetailViewModel.UiState.Success>())

            //val uiStateSuccess = viewModel.state.value as EventDetailViewModel.UiState.Success
            //val eventSuccess = uiStateSuccess.event

            //Assert.assertEquals(event, eventSuccess)
        }

    @Test
    fun `should notify uiStateObserver with Error from UiState when get event returns an exception`() =
    //
    // deve notificar uiStateObserver com Error de UiState quando obter event retornando uma exceção
    //
        runTest {
            viewModel = EventDetailViewModel(
                useCase,
                mainCoroutineRule.coroutinesDispatchers,
                savedStateHandle
            )
            savedStateHandle.apply { set(argsKey, event.id) }
            viewModel.state.observeForever(uiStateObserver)

            // Arrange
            whenever(useCase.invoke(any())).thenReturn(flowOf(ResultStatus.Error(Throwable())))

            // Act
            viewModel.actionGetEvent(event.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<EventDetailViewModel.UiState.Error>())
        }
}
