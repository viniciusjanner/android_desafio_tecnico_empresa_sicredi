package com.viniciusjanner.desafio.core.usecase.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

@Suppress("unused")
abstract class UseCase<in P, R> {
    //
    // operator : nos permite suprimir o .invoke na chamada do método.
    //
    operator fun invoke(params: P): Flow<ResultStatus<R>> =
        flow {
            emit(ResultStatus.Loading)
            emit(doWork(params))
        }.catch { throwable ->
            emit(ResultStatus.Error(throwable))
        }

    protected abstract suspend fun doWork(params: P): ResultStatus<R>
}

@Suppress("unused")
abstract class FlowUseCase<in P, R : Any> {
    //
    // operator : nos permite suprimir o .invoke na chamada do método.
    //
    suspend operator fun invoke(params: P): Flow<R> = createFlowObservable(params)

    protected abstract suspend fun createFlowObservable(params: P): Flow<R>
}
