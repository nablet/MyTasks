package com.nablet.mytasks.android.presentation.task_list

import androidx.compose.runtime.Composable
import com.nablet.mytasks.android.presentation.task_list.components.TaskList
import com.nablet.mytasks.android.presentation.theme.AppTheme
import com.nablet.mytasks.presentation.task_list.TaskListEvent
import com.nablet.mytasks.presentation.task_list.TaskListState

@Composable
fun TasksScreen(
	state: TaskListState,
	onEvent: (TaskListEvent) -> Unit,
) {
	AppTheme(
		displayProgressBar = state.isLoading,
		dialogQueue = state.queue,
		onRemoveHeadMessageFromQueue = {
			onEvent(TaskListEvent.OnRemoveHeadMessageFromQueue)
		}
	) {
		TaskList(
			tasks = state.tasks,
			onClick = { task -> onEvent(TaskListEvent.OnClick(task)) },
			onLongClick = { task -> onEvent(TaskListEvent.OnLongClick(task)) }
		)
	}
}
