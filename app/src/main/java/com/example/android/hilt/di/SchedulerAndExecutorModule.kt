package com.example.android.hilt.di

import android.os.Handler
import android.os.Looper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.*
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object SchedulerAndExecutorModule {

    @Provides
    @Singleton
    fun provideMainHandler(): Handler {
        return Handler(Looper.getMainLooper())
    }

    @Provides
    @Singleton
    fun provideExecutorService(): ExecutorService {
        return Executors.newFixedThreadPool(EXECUTOR_POOL_SIZE)
    }

    private const val EXECUTOR_POOL_SIZE = 4
}
