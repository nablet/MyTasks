package com.nablet.mytasks.di

import com.nablet.mytasks.datasource.cache.TasksRepository
import com.nablet.mytasks.datasource.network.GithubService
import com.nablet.mytasks.domain.util.DateTimeUtil
import com.nablet.mytasks.usecases.AddTask
import com.nablet.mytasks.usecases.CheckVersion
import com.nablet.mytasks.usecases.DeleteTask
import com.nablet.mytasks.usecases.LoadTasks

class UseCasesModule(
	private val tasksRepository: TasksRepository,
	private val githubService: GithubService,
) {

	val checkVersion: CheckVersion by lazy {
		CheckVersion(githubService = githubService)
	}

	val loadTasks: LoadTasks by lazy {
		LoadTasks(tasksRepository = tasksRepository)
	}

	val addTask: AddTask by lazy {
		AddTask(
			tasksRepository = tasksRepository,
			dateTimeUtil = DateTimeUtil.instance
		)
	}

	val deleteTask: DeleteTask by lazy {
		DeleteTask(tasksRepository = tasksRepository)
	}

}
