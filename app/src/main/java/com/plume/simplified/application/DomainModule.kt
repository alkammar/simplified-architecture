package com.plume.simplified.application

import com.plume.domain.infra.UseCaseExecutor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun providesUseCaseExecutor(
        @Named("main") mainCoroutineScope: CoroutineScope,
        @Named("io") ioCoroutineScope: CoroutineScope
    ) = UseCaseExecutor(mainCoroutineScope, ioCoroutineScope)
}