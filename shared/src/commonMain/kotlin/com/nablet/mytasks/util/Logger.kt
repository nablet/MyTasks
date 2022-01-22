package com.nablet.mytasks.util

expect class Logger(
	className: String
) {
	fun log(msg: String)
}

fun printLogDebug(className: String?, message: String) {
	println("$className: $message")
}
