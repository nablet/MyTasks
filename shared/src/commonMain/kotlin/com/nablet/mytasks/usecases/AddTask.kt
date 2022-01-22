package com.nablet.mytasks.usecases

import com.nablet.mytasks.datasource.cache.TasksRepository
import com.nablet.mytasks.domain.model.GenericMessageInfo
import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.domain.model.toGenericMessageOrNull
import com.nablet.mytasks.domain.util.DateTimeUtil
import com.nablet.mytasks.util.Logger

class AddTask(
	private val tasksRepository: TasksRepository,
	private val dateTimeUtil: DateTimeUtil,
) {
	private val logger = Logger("AddTask")

	fun execute(
		taskName: String,
		taskDesc: String,
	): GenericMessageInfo.Builder? {
		return runCatching {
			val trimmedName = taskName.trim()
			val trimmedDesc = taskDesc.trim()
			if (trimmedName.isBlank()) {
				throw Exception("Task name cannot be empty")
			}
			val taskItem = Task(
				name = trimmedName,
				description = trimmedDesc.toNullIfBlank(),
				localDateTime = dateTimeUtil.now()
			)
			tasksRepository.insertTask(taskItem)
		}.toGenericMessageOrNull("AddTask")
	}

	private fun String.toNullIfBlank(): String? {
		return this.ifBlank { null }
	}

}
