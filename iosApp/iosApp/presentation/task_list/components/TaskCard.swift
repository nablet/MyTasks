//
//  TaskCard.swift
//  iosApp
//
//  Created by Jayson Tiongco on 1/25/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TaskCard: View {
    
    let screenSize = UIScreen.main.bounds
    let task: Task
    let dateTimeUtil: DateTimeUtil
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(task.name).font(Quicksand.regular)
            if let desc = task.description_ {
                Text(desc)
                    .font(Quicksand.light)
            }
            Text("Created at " + dateTimeUtil.humanizeDatetime(
                date: task.localDateTime))
                .font(Quicksand.regular)
        }
        .padding()
        .frame(
            minWidth: 0,
            maxWidth: .infinity,
            alignment: .leading
        )
        .listRowInsets(EdgeInsets())
    }
}

struct TaskCard_Previews: PreviewProvider {
    static var previews: some View {
        TaskCard(
            task: Task(
                name: "Task name",
                description: "Task description",
                localDateTime: DateTimeUtil().now()
            ),
            dateTimeUtil: DateTimeUtil()
        )
    }
}
