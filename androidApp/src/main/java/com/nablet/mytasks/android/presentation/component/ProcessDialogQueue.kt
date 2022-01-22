package com.nablet.mytasks.android.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nablet.mytasks.domain.model.GenericMessageInfo
import com.nablet.mytasks.domain.model.UIComponentType
import com.nablet.mytasks.domain.util.Queue
import kotlinx.coroutines.delay

@Composable
fun ProcessDialogQueue(
	dialogQueue: Queue<GenericMessageInfo>?,
	onRemoveHeadMessageFromQueue: () -> Unit,
) {
	dialogQueue?.peek()?.let { dialogInfo ->
		when (dialogInfo.uiComponentType) {
			UIComponentType.Dialog -> {
				GenericDialog(
					onDismiss = dialogInfo.onDismiss,
					title = dialogInfo.title,
					description = dialogInfo.description,
					positiveAction = dialogInfo.positiveAction,
					negativeAction = dialogInfo.negativeAction,
					onRemoveHeadFromQueue = onRemoveHeadMessageFromQueue
				)
			}
			UIComponentType.SnackBar -> {
				val visible = remember { mutableStateOf(true) }

				if (visible.value) {
					Snackbar(
						modifier = Modifier.padding(6.dp),
					) {
						Text(text = dialogInfo.description ?: dialogInfo.title)
					}
					LaunchedEffect(key1 = "snackbar") {
						delay(2000)
						visible.value = false
						onRemoveHeadMessageFromQueue.invoke()
					}
				}
			}
			else -> {

			}
		}
	}
}
