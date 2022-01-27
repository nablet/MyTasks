//
//  AddTaskView.swift
//  iosApp
//
//  Created by Jayson Tiongco on 1/27/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AddTaskView: View {
    
    @Binding var isShowing: Bool
    @State var taskName: String
    @State var taskDesc: String
    var onConfirm: (TaskListEvent) -> Void
    
    init(
        isShowing: Binding<Bool>,
        onConfirm: @escaping (TaskListEvent) -> Void
    ) {
        _isShowing = isShowing
        self.onConfirm = onConfirm
        self.taskName = ""
        self.taskDesc = ""
    }
    
    var body: some View {
        VStack {
            Text("Create task:").fontWeight(.bold)
            Spacer()
            TextField("Short task name", text: $taskName)
            TextField("Optional task description", text: $taskDesc)
            Spacer()
            Button(action: {
                isShowing.toggle()
                onConfirm(
                    TaskListEvent.AddTask(
                        name: taskName,
                        desc: taskDesc
                    )
                )
            }) {
                Text("Add task")
                    .autocapitalization(.allCharacters)
                    .frame(minWidth: 0, maxWidth: .infinity)
                    .padding()
            }
        }.padding()
    }
}
