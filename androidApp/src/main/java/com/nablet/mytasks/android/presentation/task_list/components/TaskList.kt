package com.nablet.mytasks.android.presentation.task_list.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.nablet.mytasks.android.presentation.theme.Green500
import com.nablet.mytasks.android.presentation.theme.Grey400
import com.nablet.mytasks.android.presentation.theme.White1000
import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.presentation.task_list.TaskListEvent

private val ListComparator = Comparator<Task> { left, right ->
	left.localDateTime.toString().compareTo(right.localDateTime.toString())
}

@ExperimentalMaterialApi
@Composable
fun TaskList(
	tasks: List<Task>,
	onUserEvent: (TaskListEvent) -> Unit,
) {
	val comparator by remember { mutableStateOf(ListComparator) }

	Box(
		modifier = Modifier.background(color = MaterialTheme.colors.surface)
	) {
		LazyColumn {
			items(
				items = tasks.sortedWith(comparator),
				key = { it.localDateTime.toString() }
			) { taskItem ->
				TaskCard(
					taskItem = taskItem,
					onUserEvent = onUserEvent
				)
			}
		}
	}
}

@ExperimentalMaterialApi
@Composable
fun TaskCard(
	taskItem: Task,
	onUserEvent: (TaskListEvent) -> Unit,
) {
	val dismissState = rememberDismissState()
	if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
		onUserEvent(TaskListEvent.OnSwipeRight(taskItem))
	}
	if (dismissState.isDismissed(DismissDirection.EndToStart)) {
		onUserEvent(TaskListEvent.OnSwipeLeft(taskItem))
	}

	SwipeToDismiss(
		state = dismissState,
		background = {
			val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
			val color by animateColorAsState(
				when (dismissState.targetValue) {
					DismissValue.Default -> MaterialTheme.colors.background
					DismissValue.DismissedToEnd -> Green500
					DismissValue.DismissedToStart -> Green500
				}
			)
			val alignment = when (direction) {
				DismissDirection.StartToEnd -> Alignment.CenterStart
				DismissDirection.EndToStart -> Alignment.CenterEnd
			}
			val icon = when (direction) {
				DismissDirection.StartToEnd -> Icons.Rounded.CheckCircle
				DismissDirection.EndToStart -> Icons.Rounded.CheckCircle
			}
			val scale by animateFloatAsState(
				if (dismissState.targetValue == DismissValue.Default) 0.7f else 1.2f
			)
			val iconColor by animateColorAsState(
				when (dismissState.targetValue) {
					DismissValue.Default -> Grey400
					DismissValue.DismissedToEnd -> White1000
					DismissValue.DismissedToStart -> White1000
				}
			)
			Box(
				contentAlignment = alignment,
				modifier = Modifier
					.fillMaxSize()
					.background(color)
					.padding(horizontal = 20.dp),
			) {
				Icon(
					imageVector = icon,
					contentDescription = "Localized description",
					modifier = Modifier
						.scale(scale),
					tint = iconColor
				)
			}
		}
	) {
		TaskCard(
			cardElevation = animateDpAsState(
				if (dismissState.dismissDirection != null)
					4.dp else 0.dp
			),
			task = taskItem,
			onItemClick = { onUserEvent(TaskListEvent.OnClick(taskItem)) },
			onItemLongClick = { onUserEvent(TaskListEvent.OnLongClick(taskItem)) }
		)
	}
}
