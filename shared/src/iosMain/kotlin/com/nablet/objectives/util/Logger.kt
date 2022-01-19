package com.nablet.objectives.util

actual class Logger actual constructor(
	private val className: String
) {
	actual fun log(msg: String) {
		if (!BuildConfig().isDebug()) {
			// Production logging
		} else {
			printLogDebug(className, msg)
		}
	}
}
