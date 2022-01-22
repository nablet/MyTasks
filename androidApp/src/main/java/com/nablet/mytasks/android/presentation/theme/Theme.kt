package com.nablet.mytasks.android.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nablet.mytasks.android.presentation.component.CircularIndeterminateProgressBar

private val LightThemeColors = lightColors(
	primary = Blue600,
	primaryVariant = Blue400,
	onPrimary = LightBlue900,
	secondary = BlueA100,
	secondaryVariant = Teal300,
	onSecondary = Grey800,
	error = Red300,
	onError = Red200,
	background = White1000,
	onBackground = Grey800,
	surface = White1000,
	onSurface = Grey800,
)

@Composable
fun AppTheme(
	displayProgressBar: Boolean,
	content: @Composable () -> Unit,
) {
	MaterialTheme(
		colors = LightThemeColors,
		typography = QuickSandTypography,
		shapes = AppShapes
	) {
		Box(modifier = Modifier
			.fillMaxSize()
			.background(color = White1000)
		) {
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
