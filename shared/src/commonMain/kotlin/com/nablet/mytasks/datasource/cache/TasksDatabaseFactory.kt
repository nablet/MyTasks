package com.nablet.mytasks.datasource.cache

import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.domain.util.DateTimeUtil
import com.squareup.sqldelight.db.SqlDriver

class TasksDatabaseFactory(private val driverFactory: DriverFactory) {
	fun createDatabase(): TasksDatabase {
		return TasksDatabase(driverFactory.createDriver())
	}
}

expect class DriverFactory {
	fun createDriver(): SqlDriver
}

fun List<Task_Entity>.toTasksList(): List<Task> {
	return map { it.toTask() }
}

fun Task_Entity.toTask(): Task {
	val datetimeUtil = DateTimeUtil.instance
	return Task(
		name = name,
		description = desc,
		localDateTime = datetimeUtil.toLocalDate(local_date_time),
	)
}
