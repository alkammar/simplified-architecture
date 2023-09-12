package com.plume.simplified.application

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.plume.data.persistence.Persistence
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {
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
}