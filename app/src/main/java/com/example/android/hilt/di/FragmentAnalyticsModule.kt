package com.example.android.hilt.di

import analytics.AnalyticsService
import analytics.AnalyticsServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 * This module will demo how FragmentScope affects the lifecycle of the created components.
 * Learn about providing @Bind an interface info to Hilt from here.
 * Check the logcat and filter 'AnalyticsHilt' to see instance creations/destroy time.
 */
@InstallIn(FragmentComponent::class)
@Module
abstract class FragmentAnalyticsModule {

    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceImpl: AnalyticsServiceImpl
    ): AnalyticsService
}
