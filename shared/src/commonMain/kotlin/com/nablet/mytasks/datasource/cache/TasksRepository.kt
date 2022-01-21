package com.nablet.mytasks.datasource.cache

import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.domain.util.DateTimeUtil
import com.nablet.mytasks.domain.util.Failure
import com.nablet.mytasks.domain.util.Result
import com.nablet.mytasks.domain.util.Success

interface TasksRepository {
	fun insertTask(taskItem: Task)
	fun getTasks(): Result<List<Task>>
	fun deleteTask(taskName: String)
}

class TasksRepositoryImpl(
	private val tasksDatabase: TasksDatabase,
	private val dateTimeUtil: DateTimeUtil,
) : TasksRepository {

	private var queries: TasksDatabaseQueries = tasksDatabase.tasksDatabaseQueries

	override fun insertTask(taskItem: Task) {
		queries.insertTask(
			name = taskItem.name,
			desc = taskItem.description,
			local_date_time = dateTimeUtil.toEpochMilliseconds(dateTimeUtil.now())
		)
	}

	override fun getTasks(): Result<List<Task>> {
		return runCatching {
			queries.getTasks().executeAsList().toTasksList()
		}.fold(
			onSuccess = { Success(it) },
			onFailure = { Failure(it) }
		)
	}

	override fun deleteTask(taskName: String) {
		queries.deleteTask(taskName)
	}

}
