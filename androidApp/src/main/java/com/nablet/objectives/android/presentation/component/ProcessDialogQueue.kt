package com.nablet.objectives.android.presentation.component

import androidx.compose.runtime.Composable
import com.nablet.objectives.domain.model.GenericMessageInfo
import com.nablet.objectives.domain.util.Queue

@Composable
fun ProcessDialogQueue(
	dialogQueue: Queue<GenericMessageInfo>?,
	onRemoveHeadMessageFromQueue: () -> Unit,
) {
	dialogQueue?.peek()?.let { dialogInfo ->
		GenericDialog(
			onDismiss = dialogInfo.onDismiss,
			title = dialogInfo.title,
			description = dialogInfo.description,
			positiveAction = dialogInfo.positiveAction,
			negativeAction = dialogInfo.negativeAction,
			onRemoveHeadFromQueue = onRemoveHeadMessageFromQueue
		)
	}
}
