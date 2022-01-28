//
//  TaskList.swift
//  iosApp
//
//  Created by Jayson Tiongco on 1/25/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TaskList : View {
    
    let tasks: [Task]
    let dateTimeUtil = DateTimeUtil()
    var onSwipe: (Task) -> Void
    
    var body: some View {
        List {
            ForEach(tasks, id: \.self.name) { task in
                TaskCard(task: task, dateTimeUtil: dateTimeUtil)
                    .contentShape(Rectangle())
                    .modifier(SwipeToDismissModifier { onSwipe(task) })
            }
        }
        .listStyle(GroupedListStyle())
        .animation(Animation.interactiveSpring())
    }
}
