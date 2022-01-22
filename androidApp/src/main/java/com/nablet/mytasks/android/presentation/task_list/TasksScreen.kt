package com.nablet.mytasks.android.presentation.task_list

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.nablet.mytasks.android.presentation.task_list.components.AddTaskDialog
import com.nablet.mytasks.android.presentation.task_list.components.TaskList
import com.nablet.mytasks.android.presentation.theme.AppTheme
import com.nablet.mytasks.presentation.task_list.TaskListEvent
import com.nablet.mytasks.presentation.task_list.TaskListState

@Composable
fun TasksScreen(
	state: TaskListState,
	onEvent: (TaskListEvent) -> Unit,
) {
	val showAddTaskDialog = remember { mutableStateOf(false) }

	AppTheme(
		displayProgressBar = state.isLoading,
		dialogQueue = state.queue,
		onRemoveHeadMessageFromQueue = {
			onEvent(TaskListEvent.OnRemoveHeadMessageFromQueue)
		}
	) {
		Scaffold(
			floatingActionButton = {
				FloatingActionButton(
					onClick = { showAddTaskDialog.value = true },
				) {
					Icon(Icons.Rounded.Add, "AddTaskButton")
				}
			},
			content = {
				TaskList(
					tasks = state.tasks,
					onClick = { task -> onEvent(TaskListEvent.OnClick(task)) },
					onLongClick = { task -> onEvent(TaskListEvent.OnLongClick(task)) }
				)
			}
		)
		if (showAddTaskDialog.value) {
			AddTaskDialog(
				onClickAddTask = { name: String, desc: String ->
					onEvent(TaskListEvent.AddTask(name, desc))
				},
				onDismiss = { showAddTaskDialog.value = false }
			)
		}
	}
}
