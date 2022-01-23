package com.nablet.mytasks.android.presentation.task_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddTaskDialog(
	onClickAddTask: (name: String, desc: String) -> Unit,
	onDismiss: () -> Unit,
) {
	Dialog(
		onDismissRequest = onDismiss,
		content = {
			AddTaskDialogContent(
				onClickAddTask::invoke,
				onDismiss::invoke
			)
		}
	)
}

// Create separate composable for content to use for preview

@Composable
fun AddTaskDialogContent(
	onClickAddTask: (name: String, desc: String) -> Unit,
	onDismiss: () -> Unit,
) {
	val taskNameInput = remember { mutableStateOf("") }
	val taskDescInput = remember { mutableStateOf("") }

	Card(
		modifier = Modifier.wrapContentHeight(unbounded = true)
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Spacer(modifier = Modifier.height(10.dp))
			Text(
				text = "Create task:",
				modifier = Modifier
					.fillMaxWidth()
					.align(Alignment.Start)
					.padding(start = 22.dp)
			)
			Spacer(modifier = Modifier.height(6.dp))
			OutlinedTextField(
				value = taskNameInput.value,
				maxLines = 2,
				onValueChange = { taskNameInput.value = it },
				label = { Text(text = "Short task name") },
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 22.dp)
			)
			OutlinedTextField(
				value = taskDescInput.value,
				maxLines = 2,
				onValueChange = { taskDescInput.value = it },
				label = { Text(text = "Optional task description") },
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 22.dp)
			)
			Spacer(modifier = Modifier.height(8.dp))
			Row(
				horizontalArrangement = Arrangement.End,
				modifier = Modifier
					.fillMaxWidth()
					.padding(end = 6.dp)
			) {
				TextButton(onClick = {
					onClickAddTask(taskNameInput.value, taskDescInput.value)
					onDismiss()
				}) {
					Text(
						text = "ADD",
						color = MaterialTheme.colors.onSurface
					)
				}
			}
		}
	}
}
