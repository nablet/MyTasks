package com.nablet.mytasks.android.presentation.task_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nablet.mytasks.android.presentation.theme.White1000
import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.domain.util.DateTimeUtil
import kotlinx.datetime.LocalDateTime

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun PreviewTaskCard() {
	val sampleDate = LocalDateTime(2022, 1, 20, 20, 6, 40)
	val sampleTask = Task(
		name = "Task name",
		description = "The quick brown fox jumps over the lazy dog.",
		localDateTime = sampleDate
	)
	val state = remember { mutableStateOf(6.dp) }
	TaskCard(cardElevation = state, task = sampleTask, onItemClick = {}) {

	}
}

@Composable
fun TaskCard(
	cardElevation: State<Dp>,
	task: Task,
	onItemClick: () -> Unit,
	onItemLongClick: () -> Unit,
) {
	Card(
		shape = MaterialTheme.shapes.small,
		modifier = Modifier
			.padding(6.dp)
			.fillMaxWidth()
			.clickable { onItemClick.invoke() },
		elevation = cardElevation.value
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(6.dp)
		) {
			Text(
				text = task.name,
				modifier = Modifier
					.fillMaxWidth(0.85f)
					.wrapContentWidth(Alignment.Start),
				style = MaterialTheme.typography.h6
			)
			task.description?.also { taskDescription ->
				Text(
					text = taskDescription,
					modifier = Modifier
						.fillMaxWidth(0.85f)
						.wrapContentWidth(Alignment.Start),
					style = MaterialTheme.typography.subtitle1
				)
			}
			Text(
				text = "Created ${DateTimeUtil.instance.humanizeDatetime(task.localDateTime)}",
				modifier = Modifier
					.fillMaxWidth(0.85f)
					.wrapContentWidth(Alignment.Start),
				style = MaterialTheme.typography.subtitle2
			)
		}
	}
}
