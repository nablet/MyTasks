//
//  TasksViewModel.swift
//  iosApp
//
//  Created by Jayson Tiongco on 1/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

class TasksViewMode : ObservableObject {
    
    private let logger = Logger(className: "TasksViewModel")

    // UseCases
    let addTaskUseCase: AddTask
    let deleteTaskUseCase: DeleteTask
    let loadTasksUseCase: LoadTasks

    // State
    @Published var state: TaskListState = TaskListState()
    @Published var showDialog: Bool = false

    init (
        addTaskUseCase: AddTask,
        deleteTaskUseCase: DeleteTask,
        loadTasksUseCase: LoadTasks
    ) {
        self.addTaskUseCase = addTaskUseCase
        self.deleteTaskUseCase = deleteTaskUseCase
        self.loadTasksUseCase = loadTasksUseCase
        
        loadTasks()
    }
    
    func doNothing() {}
    
    func onUserEvent(userEvent: TaskListEvent) {
        switch userEvent {
        case is TaskListEvent.AddTask: doNothing()
        case is TaskListEvent.OnClick: doNothing()
        case is TaskListEvent.OnLongClick: doNothing()
        case is TaskListEvent.OnSwipeLeft: doNothing()
        case is TaskListEvent.OnSwipeRight: doNothing()
        case is TaskListEvent.OnRemoveHeadMessageFromQueue: doNothing()
        default: doNothing()
        }
    }
    
    private func updateState(
        loading: Bool? = nil,
        taskList: [Task]? = nil,
        queue: Queue<GenericMessageInfo>? = nil
    ) {
        
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
    
    private func addTask(taskItem: Task) {
        
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
    
}
