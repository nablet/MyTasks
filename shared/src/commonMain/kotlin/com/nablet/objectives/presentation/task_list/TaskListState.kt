package com.nablet.objectives.presentation.task_list

import com.nablet.objectives.domain.model.GenericMessageInfo
import com.nablet.objectives.domain.model.Task
import com.nablet.objectives.domain.util.Queue

data class TaskListState(
	val isLoading: Boolean = false,
	val tasks: List<Task> = listOf(),
	val queue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
)
