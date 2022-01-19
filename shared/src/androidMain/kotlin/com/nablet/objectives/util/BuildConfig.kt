package com.nablet.objectives.util

import com.nablet.objectives.BuildConfig

actual class BuildConfig {
	actual fun isDebug() = BuildConfig.DEBUG
	actual fun isAndroid() = true
}
