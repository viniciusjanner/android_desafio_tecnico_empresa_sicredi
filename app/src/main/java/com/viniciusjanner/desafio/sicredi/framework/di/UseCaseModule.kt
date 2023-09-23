package com.viniciusjanner.desafio.sicredi.framework.di

import com.viniciusjanner.desafio.core.usecase.EventCheckinUseCase
import com.viniciusjanner.desafio.core.usecase.EventCheckinUseCaseImpl
import com.viniciusjanner.desafio.core.usecase.EventDetailUseCase
import com.viniciusjanner.desafio.core.usecase.EventDetailUseCaseImpl
import com.viniciusjanner.desafio.core.usecase.EventListUseCase
import com.viniciusjanner.desafio.core.usecase.EventUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Suppress("unused")
@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindEventsUseCase(useCase: EventUseCaseImpl): EventListUseCase

    @Binds
    fun bindEventDetailUseCase(useCase: EventDetailUseCaseImpl): EventDetailUseCase

    @Binds
    fun bindEventCheckinUseCase(useCase: EventCheckinUseCaseImpl): EventCheckinUseCase
}
