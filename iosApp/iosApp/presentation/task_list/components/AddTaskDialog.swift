//
//  AddTaskView.swift
//  iosApp
//
//  Created by Jayson Tiongco on 1/27/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AddTaskDialog: View {
    
    let screenSize = UIScreen.main.bounds
    
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
            Text("Create task:")
                .font(Quicksand.regular)
                .padding()
            TextField("Short task name", text: $taskName)
                .font(Quicksand.regular)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            TextField("Optional task description", text: $taskDesc)
                .font(Quicksand.regular)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            HStack {
                Button(
                    action: {
                        isShowing.toggle()
                        onConfirm(
                            TaskListEvent.AddTask(
                                name: taskName,
                                desc: taskDesc
                            )
                        )
                        taskName = ""
                        taskDesc = ""
                    }
                ) {
                    Text("Add task")
                        .font(Quicksand.bold)
                        .frame(width: 80.0)
                        .padding()
                }
                Button(action: { isShowing.toggle() }) {
                    Text("Cancel")
                        .font(Quicksand.bold)
                        .frame(width: 80.0)
                        .padding()
                }
            }
        }
        .padding()
        .frame(
            width: screenSize.width * 0.7,
            height: screenSize.height * 0.24
        )
        .background(Color(UIColor.systemBackground))
        .clipShape(RoundedRectangle(cornerRadius: 14.0, style: .continuous))
        .shadow(radius: 1)
        .offset(y: isShowing ? 0 : screenSize.height)
        .animation(.spring())
    }
}

struct AddTaskDialog_Previews: PreviewProvider {
    static var previews: some View {
        AddTaskDialog(isShowing: .constant(true)) { event in
        }
    }
}
