package com.viniciusjanner.desafio.sicredi.framework.usecase.feature.detail

import com.nhaarman.mockitokotlin2.whenever
import com.viniciusjanner.desafio.core.data.repository.feature.detail.EventDetailRepository
import com.viniciusjanner.desafio.core.domain.model.Event
import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import com.viniciusjanner.desafio.core.usecase.feature.detail.EventDetailUseCase
import com.viniciusjanner.desafio.testing.MainCoroutineRule
import com.viniciusjanner.desafio.testing.core.domain.model.EventFactory
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
class EventDetailUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: EventDetailRepository

    private val event = EventFactory().create(EventFactory.EventFake.Event1)

    private lateinit var useCase: EventDetailUseCase

    @Before
    fun setup() {
        useCase = EventDetailUseCaseImpl(
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
            whenever(repository.getEvent(event.id)).thenReturn(event)

            // Act
            val resultFlow: Flow<ResultStatus<Event>> = useCase.invoke(EventDetailUseCase.GetEventParam(event.id))

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
            whenever(repository.getEvent(event.id)).thenAnswer { throw Throwable() }

            // Act
            val resultFlow: Flow<ResultStatus<Event>> = useCase.invoke(EventDetailUseCase.GetEventParam(event.id))

            // Assert
            val resultList = resultFlow.toList()
            Assert.assertEquals(ResultStatus.Loading, resultList[0])
            Assert.assertTrue(resultList[1] is ResultStatus.Error)
        }
}
