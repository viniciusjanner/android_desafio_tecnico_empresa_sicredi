package com.viniciusjanner.desafio.core.usecase

import com.nhaarman.mockitokotlin2.whenever
import com.viniciusjanner.desafio.core.data.repository.EventRepository
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.testing.MainCoroutineRule
import com.viniciusjanner.desafio.testing.model.EventCheckinFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
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
class EventCheckinUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: EventRepository

    private val checkinSend = EventCheckinFactory().checkinSend()
    private val checkinResponse = EventCheckinFactory().checkinResponse()

    private lateinit var eventCheckinUseCase: EventCheckinUseCase

    @Before
    fun setup() {
        eventCheckinUseCase = EventCheckinUseCaseImpl(
            repository,
            mainCoroutineRule.testDispatcherProvider,
        )
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        //
        // deve retornar Success de ResultStatus quando a request retornar com sucesso
        //
        runTest {
            whenever(repository.postEventCheckin(checkinSend)).thenReturn(checkinResponse)

            // Act
            val resultFlow: Flow<ResultStatus<EventCheckinResponse>> = eventCheckinUseCase.invoke(EventCheckinUseCase.GetEventParam(checkinSend))

            // Assert
            val resultList = resultFlow.toList()
            Assert.assertEquals(ResultStatus.Loading, resultList[0])
            Assert.assertTrue(resultList[1] is ResultStatus.Success)
        }

    @Test
    fun `should return Error from ResultStatus when get both requests return error`() =
        //
        // deve retornar Error de ResultStatus quando a request retornar com error
        //
        runTest {
            whenever(repository.postEventCheckin(checkinSend)).thenAnswer { throw Throwable() }

            // Act
            val resultFlow: Flow<ResultStatus<EventCheckinResponse>> = eventCheckinUseCase.invoke(EventCheckinUseCase.GetEventParam(checkinSend))

            // Assert
            val resultList = resultFlow.toList()
            Assert.assertEquals(ResultStatus.Loading, resultList[0])
            Assert.assertTrue(resultList[1] is ResultStatus.Error)
        }
}
