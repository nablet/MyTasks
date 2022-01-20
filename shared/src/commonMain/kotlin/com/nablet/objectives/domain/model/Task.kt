package com.nablet.objectives.domain.model

import kotlinx.datetime.LocalDateTime

data class Task(
	val name: String,
	val description: String,
	val localDateTime: LocalDateTime,
)
