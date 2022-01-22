package com.nablet.mytasks.usecases

import com.nablet.mytasks.datasource.cache.TasksRepository
import com.nablet.mytasks.domain.model.GenericMessageInfo
import com.nablet.mytasks.domain.model.toGenericMessageOrNull
import com.nablet.mytasks.util.Logger

class DeleteTask(private val tasksRepository: TasksRepository) {

	private val logger = Logger("DeleteTask")

	fun execute(taskName: String): GenericMessageInfo.Builder? {
		return runCatching {
			tasksRepository.deleteTask(taskName)
		}.toGenericMessageOrNull("DeleteTask")
	}

}
