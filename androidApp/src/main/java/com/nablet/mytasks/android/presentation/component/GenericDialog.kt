package com.nablet.mytasks.android.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nablet.mytasks.domain.model.NegativeAction
import com.nablet.mytasks.domain.model.PositiveAction

@Composable
fun GenericDialog(
	modifier: Modifier = Modifier,
	onDismiss: (() -> Unit)?,
	title: String,
	description: String? = null,
	positiveAction: PositiveAction?,
	negativeAction: NegativeAction?,
	onRemoveHeadFromQueue: () -> Unit,
) {
	AlertDialog(
		modifier = modifier,
		onDismissRequest = {
			onDismiss?.invoke()
			onRemoveHeadFromQueue()
		},
		title = { Text(text = title) },
		text = { description?.also { Text(text = description) } },
		buttons = {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				horizontalArrangement = Arrangement.End
			) {
				negativeAction?.also {
					Button(
						modifier = Modifier.padding(end = 8.dp),
						colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onError),
						onClick = {
							negativeAction.onNegativeAction()
							onRemoveHeadFromQueue()
						}
					) {
						Text(text = negativeAction.negativeBtnTxt)
					}
				}
				positiveAction?.also {
					TextButton(
						modifier = Modifier.padding(end = 8.dp),
						onClick = {
							positiveAction.onPositiveAction()
							onRemoveHeadFromQueue()
						}
					) {
						Text(
							text = positiveAction.positiveBtnTxt,
							color = MaterialTheme.colors.onSurface
						)
					}
				}
			}
		}
	)
}
