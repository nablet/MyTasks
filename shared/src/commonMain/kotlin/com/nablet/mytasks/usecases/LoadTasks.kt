package com.nablet.mytasks.usecases

import com.nablet.mytasks.datasource.cache.TasksRepository
import com.nablet.mytasks.domain.model.GenericMessageInfo
import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.domain.model.UIComponentType
import com.nablet.mytasks.domain.util.CommonFlow
import com.nablet.mytasks.domain.util.Failure
import com.nablet.mytasks.domain.util.Success
import com.nablet.mytasks.domain.util.asCommonFlow
import com.nablet.mytasks.util.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

sealed class LoadTasksOutput {
	data class Loading(val loading: Boolean) : LoadTasksOutput()
	data class Data(val tasks: List<Task>) : LoadTasksOutput()
	data class Error(val message: GenericMessageInfo.Builder) : LoadTasksOutput()
}

class LoadTasks(private val tasksRepository: TasksRepository) {

	private val logger = Logger("LoadTasks")

	fun execute(): CommonFlow<LoadTasksOutput> = flow {
		emit(LoadTasksOutput.Loading(true))
		val tasks = when (val result = tasksRepository.getTasks()) {
			is Success -> result.value
			is Failure -> throw result.exception
		}
		emit(LoadTasksOutput.Data(tasks))
	}.catch {
		logger.log(it.message ?: it.stackTraceToString())
		emit(LoadTasksOutput.Error(
			message = GenericMessageInfo.Builder()
				.id("LoadTasks.Error")
				.title("Error")
				.uiComponentType(UIComponentType.Dialog)
				.description(it.message ?: "Unknown error!")
		))
	}.onCompletion {
		emit(LoadTasksOutput.Loading(false))
	}.asCommonFlow()

}
