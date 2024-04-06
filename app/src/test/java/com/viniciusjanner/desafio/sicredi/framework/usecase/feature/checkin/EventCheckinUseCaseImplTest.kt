package com.viniciusjanner.desafio.sicredi.framework.usecase.feature.checkin

import com.nhaarman.mockitokotlin2.whenever
import com.viniciusjanner.desafio.core.data.repository.feature.checkin.EventCheckinRepository
import com.viniciusjanner.desafio.core.domain.model.EventCheckinResponse
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.feature.checkin.EventCheckinUseCase
import com.viniciusjanner.desafio.testing.MainCoroutineRule
import com.viniciusjanner.desafio.testing.core.domain.model.EventCheckinResponseFactory
import com.viniciusjanner.desafio.testing.core.domain.model.EventCheckinSendFactory
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
    lateinit var repository: EventCheckinRepository

    private val checkinSend = EventCheckinSendFactory().create()
    private val checkinResponse = EventCheckinResponseFactory().create()

    private lateinit var useCase: EventCheckinUseCase

    @Before
    fun setup() {
        useCase = EventCheckinUseCaseImpl(
            repository,
            mainCoroutineRule.coroutinesDispatchers,
        )
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        //
        // deve retornar Success de ResultStatus quando a request retornar com sucesso
        //
        runTest {
            // Arrange
            whenever(repository.postEventCheckin(checkinSend)).thenReturn(checkinResponse)

            // Act
            val resultFlow: Flow<ResultStatus<EventCheckinResponse>> = useCase.invoke(EventCheckinUseCase.GetEventParam(checkinSend))

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
            // Arrange
            whenever(repository.postEventCheckin(checkinSend)).thenAnswer { throw Throwable() }

            // Act
            val resultFlow: Flow<ResultStatus<EventCheckinResponse>> = useCase.invoke(EventCheckinUseCase.GetEventParam(checkinSend))

            // Assert
            val resultList = resultFlow.toList()
            Assert.assertEquals(ResultStatus.Loading, resultList[0])
            Assert.assertTrue(resultList[1] is ResultStatus.Error)
        }
}
