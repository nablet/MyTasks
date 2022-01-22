package com.nablet.mytasks.android.presentation.navigation

sealed class Screen(val route: String) {
	object TaskList : Screen("taskList")
}
