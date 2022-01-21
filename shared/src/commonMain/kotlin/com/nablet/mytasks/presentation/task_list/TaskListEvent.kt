package com.nablet.mytasks.presentation.task_list

import com.nablet.mytasks.domain.model.Task

sealed class TaskListEvent {
	data class AddTask(val newTask: Task) : TaskListEvent()
	data class OnClick(val selectedTask: Task) : TaskListEvent()
	data class OnLongClick(val selectedTask: Task) : TaskListEvent()
	object OnRemoveHeadMessageFromQueue : TaskListEvent()
}
