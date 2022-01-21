package com.nablet.mytasks.usecases

import com.nablet.mytasks.datasource.cache.TasksRepository
import com.nablet.mytasks.domain.model.GenericMessageInfo
import com.nablet.mytasks.domain.model.Task
import com.nablet.mytasks.domain.model.UIComponentType
import com.nablet.mytasks.domain.util.*
import com.nablet.mytasks.util.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class LoadTasks(private val tasksRepository: TasksRepository) {

	private val logger = Logger("LoadTasks")

	fun execute(): CommonFlow<DataState<out List<Task>>> = flow {
		emit(Loading(true))
		delay(1000)

		val tasks = when (val result = tasksRepository.getTasks()) {
			is Success -> result.value
			is Failure -> throw result.exception
		}
		emit(Update(data = tasks))
	}.catch {
		emit(Error(
			message = GenericMessageInfo.Builder()
				.id("LoadTasks.Error")
				.title("Error")
				.uiComponentType(UIComponentType.Dialog)
				.description(it.message ?: "Unknown error!")
		))
	}.onCompletion {
		emit(Loading(false))
	}.asCommonFlow()

}
