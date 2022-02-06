package com.nablet.mytasks.android.di

import android.content.Context
import com.nablet.mytasks.datasource.cache.*
import com.nablet.mytasks.datasource.network.GithubService
import com.nablet.mytasks.datasource.network.KtorClientFactory
import com.nablet.mytasks.domain.util.DateTimeUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatasourceModule {

	@Singleton
	@Provides
	fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase {
		return TasksDatabaseFactory(driverFactory = DriverFactory(context)).createDatabase()
	}

	@Singleton
	@Provides
	fun provideKtorHttpClient(): HttpClient {
		return KtorClientFactory().build()
	}

	@Singleton
	@Provides
	fun provideTasksRepository(tasksDatabase: TasksDatabase): TasksRepository {
		return TasksRepositoryImpl(
			tasksDatabase = tasksDatabase,
			dateTimeUtil = DateTimeUtil.instance
		)
	}

	@Singleton
	@Provides
	fun provideGithubService(httpClient: HttpClient): GithubService {
		return GithubService(client = httpClient)
	}

}
