package com.nablet.objectives.domain.util

import kotlinx.datetime.*

class DateTimeUtil {

	companion object {
		val instance = DateTimeUtil()
	}

	fun now(): LocalDateTime {
		val currentMoment: Instant = Clock.System.now()
		return currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
	}

	fun toLocalDate(date: Double): LocalDateTime {
		return Instant.fromEpochMilliseconds(date.toLong())
			.toLocalDateTime(TimeZone.currentSystemDefault())
	}

	fun toEpochMilliseconds(date: LocalDateTime): Double {
		return date.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds().toDouble()
	}

	fun humanizeDatetime(date: LocalDateTime?): String {
		val sb = StringBuilder()
		date?.run {
			val hour = if (this.hour > 12) {
				(this.hour - 12).toString() + "pm"
			} else {
				if (this.hour != 0) this.hour.toString() + "am" else "midnight"
			}
			val today = now()
			val tomorrow = Clock.System.now()
				.plus(1, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
				.toLocalDateTime(TimeZone.currentSystemDefault())
			when (this.date) {
				today.date -> sb.append("Today at $hour")
				tomorrow.date -> sb.append("Tomorrow at $hour")
				else -> sb.append(this.date.month.name.lowercase() + " ${this.date.dayOfMonth}")
			}
		} ?: sb.append("Unknown")
		return sb.toString()
	}

}
