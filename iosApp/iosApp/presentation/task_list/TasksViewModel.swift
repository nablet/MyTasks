//
//  TasksViewModel.swift
//  iosApp
//
//  Created by Jayson Tiongco on 1/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

class TasksViewModel : ObservableObject {
    
    private let logger = Logger(className: "TasksViewModel")

    // UseCases
    let addTaskUseCase: AddTask
    let deleteTaskUseCase: DeleteTask
    let loadTasksUseCase: LoadTasks

    // State
    @Published var state: TaskListState = TaskListState()
    @Published var showDialog: Bool = false
    @Published var showAddTaskDialog: Bool = false

    init (usecasesModule: UseCasesModule) {
        self.addTaskUseCase = usecasesModule.addTask
        self.deleteTaskUseCase = usecasesModule.deleteTask
        self.loadTasksUseCase = usecasesModule.loadTasks
        
        for i in 1...20 {
            addTask(name: "Task name \(i)", desc: "Task description \(i)")
        }
        
        loadTasks()
    }
    
    func doNothing() {}
    
    func onUserEvent(userEvent: TaskListEvent) {
        switch userEvent {
        case is TaskListEvent.AddTask:
            let event = userEvent as! TaskListEvent.AddTask
            addTask(name: event.name, desc: event.desc)
        case is TaskListEvent.OnClick: doNothing()
        case is TaskListEvent.OnLongClick: doNothing()
        case is TaskListEvent.OnSwipeLeft: doNothing()
        case is TaskListEvent.OnSwipeRight:
            let event = userEvent as! TaskListEvent.OnSwipeRight
            deleteTask(name: event.selectedTask.name)
        case is TaskListEvent.OnRemoveHeadMessageFromQueue: removeMessageFromQueue()
        default: doNothing()
        }
    }
    
    private func loadTasks() {
        loadTasksUseCase.execute().collectCommon(coroutineScope: nil, callback: { output in
            switch output {
            case is LoadTasksOutput.Loading:
                let outputLoading = (output as! LoadTasksOutput.Loading).loading
                self.updateState(loading: outputLoading)
            case is LoadTasksOutput.Data:
                let outputTasks = (output as! LoadTasksOutput.Data).tasks
                self.updateState(taskList: outputTasks)
            case is LoadTasksOutput.Error:
                let outputError = (output as! LoadTasksOutput.Error).message
                self.handleMessage(outputError.build())
            default: self.doNothing()
            }
        })
    }
    
    private func addTask(name: String, desc: String) {
        if let resultMessage = addTaskUseCase.execute(taskName: name, taskDesc: desc) {
            handleMessage(resultMessage.build())
        }
        loadTasks()
    }
    
    private func deleteTask(name: String) {
        if let resultMessage = deleteTaskUseCase.execute(taskName: name) {
            handleMessage(resultMessage.build())
        }
        loadTasks()
    }
    
    private func updateState(
        loading: Bool? = nil,
        taskList: [Task]? = nil,
        queue: Queue<GenericMessageInfo>? = nil
    ) {
        let currentState = (self.state.copy() as! TaskListState)
        self.state = self.state.doCopy(
            isLoading: loading ?? currentState.isLoading,
            tasks: taskList ?? currentState.tasks,
            queue: queue ?? currentState.queue
        )
    }
    
    private func handleMessage(_ message: GenericMessageInfo) {
        switch message.uiComponentType {
        case UIComponentType.Dialog(): appendMessageToQueue(message)
        case UIComponentType.SnackBar(): doNothing()
        case UIComponentType.None(): logger.log(msg: "\(message.description)")
        default: doNothing()
        }
    }
    
    private func appendMessageToQueue(_ message: GenericMessageInfo) {
        let currentState = (self.state.copy() as! TaskListState)
        let queue = currentState.queue
        let queueUtil = GenericMessageInfoQueueUtil()
        if !queueUtil.doesMessageAlreadyExistInQueue(queue: queue, messageInfo: message) {
            queue.add(element: message)
            updateState(queue: queue)
        }
    }
    
    private func removeMessageFromQueue() {
        let queue = (self.state.copy() as! TaskListState).queue
        do {
            try queue.remove()
            updateState(queue: queue)
        } catch {
            self.logger.log(msg: "\(error)")
        }
    }
    
}
