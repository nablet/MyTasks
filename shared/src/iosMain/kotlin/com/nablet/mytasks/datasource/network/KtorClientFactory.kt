package com.nablet.mytasks.datasource.network

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

actual class KtorClientFactory {
	actual fun build(): HttpClient {
		return HttpClient(Darwin) {
			install(ContentNegotiation) {
				json(Json {
					prettyPrint = true
					ignoreUnknownKeys = true
					expectSuccess = false
					isLenient = true
				})
			}
			install(DefaultRequest) {
				url { protocol = URLProtocol.HTTPS }
				host = "api.github.com"
				header(HttpHeaders.Accept, "application/vnd.github.v3+json")
			}
			install(HttpCache)
			install(Logging) {
				logger = Logger.SIMPLE
				level = LogLevel.INFO
			}
		}
	}
}
