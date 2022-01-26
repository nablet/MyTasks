//
//  TasksScreen.swift
//  iosApp
//
//  Created by Jayson Tiongco on 1/23/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TasksScreen: View {
    
    private let repositoryModule: RepositoryModule
    private let usecasesModule: UseCasesModule
    
    @ObservedObject var viewModel: TasksViewModel
    @GestureState var translation: CGSize = .zero
    
    init(repositoryModule: RepositoryModule) {
        self.repositoryModule = repositoryModule
        self.usecasesModule = UseCasesModule(tasksRepository: repositoryModule.tasksRepository)
        self.viewModel = TasksViewModel(usecasesModule: usecasesModule)
    }
    
    var body: some View {
        ZStack {
            TaskList(
                tasks: viewModel.state.tasks,
                onSwipe: { task in
                    viewModel.onUserEvent(
                        userEvent: TaskListEvent.OnSwipeRight(selectedTask: task))
                }
            )
            if (viewModel.state.isLoading) {
                ProgressView("Loading tasks...")
                    .frame(alignment: Alignment.center)
            }
        }
        .floatingActionButton(
            color: Color.blue,
            image: Image("plus")
                .resizable()
                .scaledToFit()
                .padding()
                .foregroundColor(Color.white),
            action: {
                
            }
        )
        .alert(isPresented: $viewModel.showDialog, content: {
            let first = viewModel.state.queue.peek()!
            return GenericMessageInfoAlert().build(
                message: first,
                onRemoveHeadMessage: {
                    viewModel.onUserEvent(
                        userEvent: TaskListEvent.OnRemoveHeadMessageFromQueue())
                }
            )
        })
    }
}

struct TasksScreen_Previews: PreviewProvider {
    static var previews: some View {
        TasksScreen(
            repositoryModule: RepositoryModule()
        )
    }
}
