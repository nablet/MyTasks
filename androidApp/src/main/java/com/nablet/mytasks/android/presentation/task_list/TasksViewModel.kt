package com.nablet.mytasks.android.presentation.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nablet.mytasks.domain.model.GenericMessageInfo
import com.nablet.mytasks.domain.util.*
import com.nablet.mytasks.presentation.task_list.TaskListEvent
import com.nablet.mytasks.presentation.task_list.TaskListState
import com.nablet.mytasks.usecases.AddTask
import com.nablet.mytasks.usecases.DeleteTask
import com.nablet.mytasks.usecases.LoadTasks
import com.nablet.mytasks.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
	private val loadTasksUseCase: LoadTasks,
	private val addTaskUseCase: AddTask,
	private val deleteTaskUseCase: DeleteTask,
) : ViewModel() {

	private val logger = Logger("TasksViewModel")

	private val _state = MutableStateFlow(TaskListState())
	val state: StateFlow<TaskListState> get() = _state.asStateFlow()

	fun onEvent(event: TaskListEvent) {
		when (event) {
			is TaskListEvent.AddTask -> addTask(event.name, event.desc)
			is TaskListEvent.OnClick -> deleteTask(event.selectedTask.name)
			is TaskListEvent.OnLongClick -> {}
			is TaskListEvent.OnRemoveHeadMessageFromQueue -> removeHeadMessage()
		}
	}

	init {
		loadTasks()
	}

	private fun loadTasks() {
		loadTasksUseCase.execute().collectCommon(viewModelScope) { output ->
			when (output) {
				is Loading -> _state.update { it.copy(isLoading = output.loading) }
				is Update -> _state.update { it.copy(tasks = output.data) }
				is Error -> appendToMessageQueue(output.message)
			}
		}
	}

	private fun addTask(name: String, desc: String) {
		addTaskUseCase.execute(name, desc)
			?.also { appendToMessageQueue(it) }
			?: loadTasks()
	}

	private fun deleteTask(taskName: String) {
		deleteTaskUseCase.execute(taskName)
			?.also { appendToMessageQueue(it) }
			?: loadTasks()
	}

	private fun removeHeadMessage() {
		runCatching {
			val queue = state.value.queue
			queue.remove()
			_state.update { it.copy(queue = Queue(mutableListOf())) } // force recompose
			_state.update { it.copy(queue = queue) }
		}.onFailure {
			logger.log("removeHeadMessage(): ${it.message}")
		}
	}

	private fun appendToMessageQueue(message: GenericMessageInfo.Builder) {
		val alreadyExistsInQueue = GenericMessageInfoQueueUtil().doesMessageAlreadyExistInQueue(
			queue = state.value.queue,
			messageInfo = message.build()
		)
		if (!alreadyExistsInQueue) {
			_state.update {
				it.copy(queue = it.queue.also { queue ->
					queue.add(message.build())
				})
			}
		}
	}

}
