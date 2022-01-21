package com.nablet.mytasks.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nablet.mytasks.android.presentation.task_list.TasksScreen
import com.nablet.mytasks.android.presentation.task_list.TasksViewModel

@Composable
fun Navigation() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Screen.TaskList.route) {
		composable(route = Screen.TaskList.route) { backstackEntry ->
			val factory = HiltViewModelFactory(LocalContext.current, backstackEntry)
			val viewModel: TasksViewModel = viewModel(factory = factory)
			TasksScreen(
				state = viewModel.state.collectAsState().value,
				onEvent = viewModel::onEvent
			)
		}
	}
}
