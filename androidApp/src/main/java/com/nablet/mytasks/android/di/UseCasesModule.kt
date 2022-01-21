package com.nablet.mytasks.android.di

import com.nablet.mytasks.datasource.cache.TasksRepository
import com.nablet.mytasks.usecases.LoadTasks
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
	fun provideLoadTasks(tasksRepository: TasksRepository): LoadTasks {
		return LoadTasks(tasksRepository = tasksRepository)
	}

}
