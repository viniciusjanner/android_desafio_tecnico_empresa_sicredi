package com.viniciusjanner.desafio.sicredi.framework.di.module

import com.viniciusjanner.desafio.core.usecase.feature.checkin.EventCheckinUseCase
import com.viniciusjanner.desafio.core.usecase.feature.detail.EventDetailUseCase
import com.viniciusjanner.desafio.core.usecase.feature.list.EventListUseCase
import com.viniciusjanner.desafio.sicredi.framework.usecase.feature.checkin.EventCheckinUseCaseImpl
import com.viniciusjanner.desafio.sicredi.framework.usecase.feature.detail.EventDetailUseCaseImpl
import com.viniciusjanner.desafio.sicredi.framework.usecase.feature.list.EventListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Suppress("unused")
@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindEventListUseCase(useCase: EventListUseCaseImpl): EventListUseCase

    @Binds
    fun bindEventDetailUseCase(useCase: EventDetailUseCaseImpl): EventDetailUseCase

    @Binds
    fun bindEventCheckinUseCase(useCase: EventCheckinUseCaseImpl): EventCheckinUseCase
}
