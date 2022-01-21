package com.nablet.mytasks.datasource.cache

import com.squareup.sqldelight.db.SqlDriver

class TasksDatabaseFactory(private val driverFactory: DriverFactory) {
	fun createDatabase(): TasksDatabase {
		return TasksDatabase(driverFactory.createDriver())
	}
}

expect class DriverFactory {
	fun createDriver(): SqlDriver
}

/**
 * Converter function for api data -> domain entity
 */
//fun Tasks_Entity.toTasks(): Tasks {
//	val datetimeUtil = DatetimeUtil()
//	return Tasks(
//		id = id.toInt(),
//		title = title,
//		publisher = publisher,
//		featuredImage = featured_image,
//		rating = rating.toInt(),
//		sourceUrl = source_url,
//		ingredients = ingredients.convertIngredientsToList(),
//		dateAdded = datetimeUtil.toLocalDate(date_added),
//		dateUpdated = datetimeUtil.toLocalDate(date_updated),
//	)
//}

//fun List<Tasks_Entity>.toTasksList(): List<Tasks>{
//	return map{it.toTasks()}
//}
