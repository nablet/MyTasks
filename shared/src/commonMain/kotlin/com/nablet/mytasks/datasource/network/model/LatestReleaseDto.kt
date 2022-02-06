package com.nablet.mytasks.datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LatestReleaseDto(
	val tag_name: String,
	val name: String,
	val body: String,
)
