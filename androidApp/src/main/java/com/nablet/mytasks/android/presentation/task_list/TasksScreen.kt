package com.nablet.mytasks.android.presentation.task_list

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.nablet.mytasks.android.presentation.component.ProcessDialogQueue
import com.nablet.mytasks.android.presentation.task_list.components.AddTaskDialog
import com.nablet.mytasks.android.presentation.task_list.components.TaskList
import com.nablet.mytasks.android.presentation.theme.AppTheme
import com.nablet.mytasks.android.presentation.theme.White1000
import com.nablet.mytasks.presentation.task_list.TaskListEvent
import com.nablet.mytasks.presentation.task_list.TaskListState

@ExperimentalMaterialApi
@Composable
fun TasksScreen(
	state: TaskListState,
	onEvent: (TaskListEvent) -> Unit,
) {
	val showAddTaskDialog = remember { mutableStateOf(false) }
	val showGenericDialog = remember { mutableStateOf(false) }

	AppTheme(
		displayProgressBar = state.isLoading,
	) {
		Scaffold(
			floatingActionButton = {
				FloatingActionButton(
					onClick = { showAddTaskDialog.value = true },
				) {
					Icon(
						imageVector = Icons.Rounded.Add,
						contentDescription = "AddTaskButton",
						tint = MaterialTheme.colors.surface
					)
				}
			},
			content = {
				TaskList(
					tasks = state.tasks,
					onUserEvent = onEvent::invoke,
				)
			}
		)

		// Hacky fix idk
		if (!state.queue.isEmpty()) {
			showGenericDialog.value = true
		}
		if (showGenericDialog.value) {
			ProcessDialogQueue(
				dialogQueue = state.queue,
				onRemoveHeadMessageFromQueue = {
					onEvent.invoke(TaskListEvent.OnRemoveHeadMessageFromQueue)
					showGenericDialog.value = false
				}
			)
		}
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
