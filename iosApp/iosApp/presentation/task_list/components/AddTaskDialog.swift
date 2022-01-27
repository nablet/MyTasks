//
//  AddTaskDialog.swift
//  iosApp
//
//  Created by Jayson Tiongco on 1/26/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TextFieldAlert: View {

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
        if isShowing {
            GeometryReader { (deviceSize: GeometryProxy) in
                ZStack {
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
                    .background(Color.white)
                    .frame(
                        width: deviceSize.size.width * 0.3,
                        height: deviceSize.size.height * 0.3,
                        alignment: .center
                    )
                    .shadow(radius: 1)
                    .opacity(self.isShowing ? 1 : 0)
                }
            }
        }
    }
}

extension View {
    func addTaskDialog(
        isShowing: Binding<Bool>,
        onConfirm: @escaping (TaskListEvent) -> Void
    ) -> some View {
        TextFieldAlert(
            isShowing: isShowing,
            onConfirm: onConfirm
        )
    }
}
