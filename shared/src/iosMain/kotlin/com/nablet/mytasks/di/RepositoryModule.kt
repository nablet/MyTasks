package com.nablet.mytasks.di

import com.nablet.mytasks.datasource.cache.DriverFactory
import com.nablet.mytasks.datasource.cache.TasksDatabase
import com.nablet.mytasks.datasource.cache.TasksRepository
import com.nablet.mytasks.datasource.cache.TasksRepositoryImpl
import com.nablet.mytasks.datasource.network.GithubService
import com.nablet.mytasks.datasource.network.KtorClientFactory
import com.nablet.mytasks.domain.util.DateTimeUtil
import io.ktor.client.*

class RepositoryModule {

	private val driverFactory: DriverFactory by lazy {
		DriverFactory()
	}

	private val tasksDatabase: TasksDatabase by lazy {
		TasksDatabase(driverFactory.createDriver())
	}

	private val httpClient: HttpClient by lazy {
		KtorClientFactory().build()
	}

	val tasksRepository: TasksRepository by lazy {
		TasksRepositoryImpl(
			tasksDatabase = tasksDatabase,
			dateTimeUtil = DateTimeUtil.instance
		)
	}

	val githubService: GithubService by lazy {
		GithubService(
			client = httpClient
		)
	}

}
