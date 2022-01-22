package com.nablet.mytasks.util

import com.nablet.mytasks.BuildConfig

actual class BuildConfig {
	actual fun isDebug() = BuildConfig.DEBUG
	actual fun isAndroid() = true
}
