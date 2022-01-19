package com.nablet.objectives.util

actual class BuildConfig {
	actual fun isDebug() = Platform.isDebugBinary
	actual fun isAndroid() = false
}
