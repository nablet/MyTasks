package com.nablet.mytasks.android

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.nablet.mytasks.android.presentation.task_list.TasksScreen
import com.nablet.mytasks.android.presentation.task_list.components.AddTaskDialogContent
import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.domain.util.DateTimeUtil
import com.nablet.mytasks.presentation.task_list.TaskListState

@ExperimentalMaterialApi
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewTasksSceen() {
	val tasks = (0..20).map { Task("Task #$it", "Description $it", DateTimeUtil.instance.now()) }
	val state = TaskListState(
		isLoading = true,
		tasks = tasks,
	)
	TasksScreen(
		state = remember { mutableStateOf(state) }.value,
		onEvent = {}
	)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewAddTaskDialog() {
	val showDialog = remember { mutableStateOf(true) }
	if (showDialog.value) {
		AddTaskDialogContent(
			onClickAddTask = { _, _ -> },
			onDismiss = {}
		)
	}
}
