package com.plume.simplified.application

import com.plume.simplified.infra.DefaultErrorBehavior
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {
    @Provides
    fun providesDefaultErrorBehavior() = DefaultErrorBehavior()
}