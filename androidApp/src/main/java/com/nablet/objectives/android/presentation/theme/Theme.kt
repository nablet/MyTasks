package com.nablet.objectives.android.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nablet.objectives.android.presentation.component.CircularIndeterminateProgressBar
import com.nablet.objectives.android.presentation.component.ProcessDialogQueue
import com.nablet.objectives.domain.model.GenericMessageInfo
import com.nablet.objectives.domain.util.Queue

private val LightThemeColors = lightColors(
	primary = Blue600,
	primaryVariant = Blue400,
	onPrimary = Grey800,
	secondary = White1000,
	secondaryVariant = Teal300,
	onSecondary = Grey800,
	error = Red300,
	onError = Red200,
	background = BlueGrey200,
	onBackground = Black1000,
	surface = White1000,
	onSurface = Grey800,
)

@Composable
fun AppTheme(
	displayProgressBar: Boolean,
	dialogQueue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
	onRemoveHeadMessageFromQueue: () -> Unit,
	content: @Composable () -> Unit,
) {
	MaterialTheme(
		colors = LightThemeColors,
		typography = QuickSandTypography,
		shapes = AppShapes
	) {
		Box(modifier = Modifier
			.fillMaxSize()
			.background(color = BlueGrey200)
		) {
			ProcessDialogQueue(
				dialogQueue = dialogQueue,
				onRemoveHeadMessageFromQueue = onRemoveHeadMessageFromQueue
			)
			Column {
				content()
			}
			CircularIndeterminateProgressBar(
				isDisplayed = displayProgressBar,
				verticalBias = 0.3f
			)
		}
	}
}
