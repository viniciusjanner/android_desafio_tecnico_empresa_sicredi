package com.viniciusjanner.desafio.sicredi.framework.di

import com.viniciusjanner.desafio.core.usecase.EventDetailUseCase
import com.viniciusjanner.desafio.core.usecase.EventDetailUseCaseImpl
import com.viniciusjanner.desafio.core.usecase.EventsUseCase
import com.viniciusjanner.desafio.core.usecase.EventsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Suppress("unused")
@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindEventsUseCase(useCase: EventsUseCaseImpl): EventsUseCase

    @Binds
    fun bindEventDetailUseCase(useCase: EventDetailUseCaseImpl): EventDetailUseCase
}
