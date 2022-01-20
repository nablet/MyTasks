package com.nablet.objectives.usecases

import com.nablet.objectives.domain.model.GenericMessageInfo
import com.nablet.objectives.domain.model.Task
import com.nablet.objectives.domain.model.UIComponentType
import com.nablet.objectives.domain.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class LoadTasks {

	fun execute(): CommonFlow<DataState<out List<Task>>> = flow {
		emit(Loading(true))
		delay(5000)
		emit(Update(data = (0..10).map {
			Task(
				name = "Task name 1111 $it",
				description = "A quick brown fox jumps over the lazy dog 2222 $it",
				localDateTime = DateTimeUtil.instance.now()
			)
		}))
	}.catch {
		emit(Failure(
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
