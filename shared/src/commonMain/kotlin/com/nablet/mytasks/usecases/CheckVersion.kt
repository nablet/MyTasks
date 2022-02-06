package com.nablet.mytasks.usecases

import com.nablet.mytasks.datasource.network.GithubService
import com.nablet.mytasks.domain.model.GenericMessageInfo
import com.nablet.mytasks.domain.model.LatestRelease
import com.nablet.mytasks.domain.model.UIComponentType
import com.nablet.mytasks.domain.util.CommonFlow
import com.nablet.mytasks.domain.util.asCommonFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

sealed class CheckVersionStatus {
	data class Loading(val loading: Boolean) : CheckVersionStatus()
	data class Error(val message: GenericMessageInfo.Builder) : CheckVersionStatus()
	object IsLatestVersion : CheckVersionStatus()
	data class NewVersionAvailable(val message: GenericMessageInfo.Builder) : CheckVersionStatus()
}

class CheckVersion(private val githubService: GithubService) {

	fun execute(currentVersion: String): CommonFlow<CheckVersionStatus> = flow {
		emit(CheckVersionStatus.Loading(loading = true))
		val latest = githubService.getLatestRelease()
		if (currentVersion >= latest.name) {
			emit(CheckVersionStatus.IsLatestVersion)
		} else {
			emit(CheckVersionStatus.NewVersionAvailable(
				message = GenericMessageInfo.Builder()
					.id("CheckVersion.NewVersionAvailable")
					.title("${latest.tagName} is available!")
					.uiComponentType(UIComponentType.Dialog)
					.description(latest.body)
			))
		}
	}.catch {
		emit(CheckVersionStatus.Error(
			message = GenericMessageInfo.Builder()
				.id("CheckVersion.Error")
				.title("Error")
				.uiComponentType(UIComponentType.Dialog)
				.description(it.message ?: "Unknown error!")
		))
	}.onCompletion {
		emit(CheckVersionStatus.Loading(loading = false))
	}.asCommonFlow()

}
