package com.viniciusjanner.desafio.sicredi.util.extensions

import com.viniciusjanner.desafio.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<ResultStatus<T>>.watchStatus(
    //
    // Usamos {} para suprimir o bloco de codigo ao chamarmos .watchStatus
    //
    loading: suspend () -> Unit = {},
    success: suspend (data: T) -> Unit,
    error: suspend (throwable: Throwable) -> Unit,
) {
    collect { status ->
        when (status) {
            ResultStatus.Loading -> loading.invoke()

            is ResultStatus.Success -> success.invoke(status.data)

            is ResultStatus.Error -> error.invoke(status.throwable)
        }
    }
}
