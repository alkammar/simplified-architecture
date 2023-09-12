package com.plume.simplified.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutineScopeModule {
    @Provides
    @Singleton
    @Named("main")
    fun providesMainCoroutineScope() = CoroutineScope(Dispatchers.Main)

    @Provides
    @Singleton
    @Named("io")
    fun providesIoCoroutineScope() = CoroutineScope(Dispatchers.IO)
}