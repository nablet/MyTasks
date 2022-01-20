package com.nablet.objectives.android.presentation.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nablet.objectives.domain.model.GenericMessageInfo
import com.nablet.objectives.domain.util.*
import com.nablet.objectives.presentation.task_list.TaskListEvent
import com.nablet.objectives.presentation.task_list.TaskListState
import com.nablet.objectives.usecases.LoadTasks
import com.nablet.objectives.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
	private val loadTasks: LoadTasks,
) : ViewModel() {

	private val logger = Logger("TasksViewModel")

	private val _state = MutableStateFlow(TaskListState())
	val state: StateFlow<TaskListState> get() = _state.asStateFlow()

	fun onEvent(event: TaskListEvent) {
		when (event) {
			is TaskListEvent.AddTask -> {}
			is TaskListEvent.OnClick -> {}
			is TaskListEvent.OnLongClick -> {}
			is TaskListEvent.OnRemoveHeadMessageFromQueue -> removeHeadMessage()
		}
	}

	init {
		loadTasks.execute().collectCommon(viewModelScope) { output ->
			when (output) {
				is Loading -> _state.update { it.copy(isLoading = output.loading) }
				is Update -> _state.update { it.copy(tasks = output.data) }
				is Failure -> appendToMessageQueue(output.message)
			}
		}
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
