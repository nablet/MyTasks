package com.nablet.mytasks.datasource.network

import com.nablet.mytasks.datasource.network.model.LatestReleaseDto
import com.nablet.mytasks.domain.model.LatestRelease
import io.ktor.client.*

expect class KtorClientFactory {
	fun build(): HttpClient
}

fun LatestReleaseDto.toLatestRelease(): LatestRelease {
	return LatestRelease(tag_name, name, body)
}
