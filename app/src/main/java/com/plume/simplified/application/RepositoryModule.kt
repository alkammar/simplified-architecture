package com.plume.simplified.application

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.plume.data.DeviceDataRepository
import com.plume.data.MotionDataRepository
import com.plume.data.NodeDataRepository
import com.plume.data.persistence.Persistence
import com.plume.repository.DeviceRepository
import com.plume.repository.MotionRepository
import com.plume.repository.NodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("plume", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesGson() = Gson()

    @Provides
    @Singleton
    fun providesPersistence(gson: Gson, sharedPreferences: SharedPreferences): Persistence =
        DataPersistence(gson, sharedPreferences)

    @Provides
    @Singleton
    @Named("main")
    fun providesMainCoroutineScope() = CoroutineScope(Dispatchers.Main)

    @Provides
    @Singleton
    @Named("io")
    fun providesIoCoroutineScope() = CoroutineScope(Dispatchers.IO)

    @Provides
    @Singleton
    fun providesDeviceRepository(
        @Named("io") ioCoroutineScope: CoroutineScope
    ): DeviceRepository = DeviceDataRepository(ioCoroutineScope)

    @Provides
    @Singleton
    fun providesNodeRepository(
        @Named("io") ioCoroutineScope: CoroutineScope,
        persistence: Persistence
    ): NodeRepository = NodeDataRepository(ioCoroutineScope, persistence)

    @Provides
    @Singleton
    fun providesMotionRepository(
        @Named("io") ioCoroutineScope: CoroutineScope,
        persistence: Persistence
    ): MotionRepository = MotionDataRepository(ioCoroutineScope, persistence)
}