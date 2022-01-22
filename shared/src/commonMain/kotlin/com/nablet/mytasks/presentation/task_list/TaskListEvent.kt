package com.nablet.mytasks.presentation.task_list

import com.nablet.mytasks.domain.model.Task

sealed class TaskListEvent {
	data class AddTask(val name: String, val desc: String) : TaskListEvent()
	data class OnClick(val selectedTask: Task) : TaskListEvent()
	data class OnLongClick(val selectedTask: Task) : TaskListEvent()
	object OnRemoveHeadMessageFromQueue : TaskListEvent()
}
