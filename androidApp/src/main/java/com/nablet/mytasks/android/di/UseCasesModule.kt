package com.nablet.mytasks.android.di

import com.nablet.mytasks.datasource.cache.TasksRepository
import com.nablet.mytasks.datasource.network.GithubService
import com.nablet.mytasks.domain.util.DateTimeUtil
import com.nablet.mytasks.usecases.AddTask
import com.nablet.mytasks.usecases.CheckVersion
import com.nablet.mytasks.usecases.DeleteTask
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
	fun provideLoadTasksUseCase(
		tasksRepository: TasksRepository,
	): LoadTasks {
		return LoadTasks(tasksRepository = tasksRepository)
	}

	@Singleton
	@Provides
	fun provideAddTaskUseCase(
		tasksRepository: TasksRepository,
	): AddTask {
		return AddTask(
			tasksRepository = tasksRepository,
			dateTimeUtil = DateTimeUtil.instance
		)
	}

	@Singleton
	@Provides
	fun provideDeleteTaskUseCase(
		tasksRepository: TasksRepository,
	): DeleteTask {
		return DeleteTask(
			tasksRepository = tasksRepository
		)
	}

	@Singleton
	@Provides
	fun provideCheckVersionUseCase(
		githubService: GithubService
	): CheckVersion {
		return CheckVersion(
			githubService = githubService
		)
	}

}
