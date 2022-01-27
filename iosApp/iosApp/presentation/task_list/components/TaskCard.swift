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
    
    let task: Task
    let dateTimeUtil: DateTimeUtil
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(task.name)
            Text("Created at " + dateTimeUtil.humanizeDatetime(
                date: task.localDateTime))
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
                name: "haha",
                description: "xddd",
                localDateTime: DateTimeUtil().now()
            ),
            dateTimeUtil: DateTimeUtil()
        )
    }
}
