package com.nablet.mytasks.presentation.task_list

import com.nablet.mytasks.domain.model.GenericMessageInfo
import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.domain.util.Queue

data class TaskListState(
	val isLoading: Boolean = false,
	val tasks: List<Task> = listOf(),
	val queue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
) {
	constructor() : this(
		isLoading = false,
		tasks = listOf(),
		queue = Queue(mutableListOf())
	)
}
