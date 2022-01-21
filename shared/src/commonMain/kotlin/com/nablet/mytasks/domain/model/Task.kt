package com.nablet.mytasks.domain.model

import kotlinx.datetime.LocalDateTime

data class Task(
	val name: String,
	val description: String,
	val localDateTime: LocalDateTime,
)
