package com.nablet.objectives.android.presentation.task_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nablet.objectives.domain.model.Task
import kotlinx.datetime.LocalDateTime

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun PreviewTasks() {
	val sampleDate = LocalDateTime(2022, 12, 20, 20, 6, 40)
	TaskList(
		tasks = (0..10).map {
			Task(
				name = "Task name $it",
				description = "A quick brown fox jumps over the lazy dog number -> $it",
				localDateTime = sampleDate
			)
		},
		onClick = {},
		onLongClick = {}
	)
}

@Composable
fun TaskList(
	tasks: List<Task>,
	onClick: (Task) -> Unit,
	onLongClick: (Task) -> Unit,
) {
	Box(modifier = Modifier.background(color = MaterialTheme.colors.surface)) {
		LazyColumn {
			items(tasks) { taskItem ->
				TaskCard(
					task = taskItem,
					onItemClick = { onClick(taskItem) },
					onItemLongClick = { onLongClick(taskItem) }
				)
			}
		}
	}
}
