package com.nablet.objectives.android.di

import com.nablet.objectives.usecases.LoadTasks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

	@Singleton
	@Provides
	fun provideLoadTasks(): LoadTasks {
		return LoadTasks()
	}

}
