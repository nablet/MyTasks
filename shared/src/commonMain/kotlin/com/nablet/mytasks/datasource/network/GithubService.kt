package com.nablet.mytasks.datasource.network

import com.nablet.mytasks.datasource.network.model.LatestReleaseDto
import com.nablet.mytasks.domain.model.LatestRelease
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class GithubService(private val client: HttpClient) {

	suspend fun getLatestRelease(): LatestRelease {
		return client.get { url(path = "/repos/nablet/MyTasks/releases/latest") }
			.body<LatestReleaseDto>()
			.toLatestRelease()
	}

}
