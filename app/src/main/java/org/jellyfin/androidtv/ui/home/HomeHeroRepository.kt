package org.jellyfin.androidtv.ui.home

import kotlinx.coroutines.flow.first
import org.jellyfin.androidtv.auth.repository.UserRepository
import org.jellyfin.androidtv.data.repository.ItemRepository
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.request.GetLatestMediaRequest

class HomeHeroRepository(
	private val api: ApiClient,
	private val userRepository: UserRepository,
) {
	suspend fun getFeaturedItem(): BaseItemDto? {
		val user = userRepository.currentUser.first() ?: return null
		val response = api.get(
			GetLatestMediaRequest(
				userId = user.id,
				fields = ItemRepository.browseFields,
				imageTypeLimit = 1,
				parentId = null,
				includeItemTypes = setOf(BaseItemKind.MOVIE, BaseItemKind.SERIES),
				groupItems = false,
				limit = 20,
			)
		)
		return response.content?.firstOrNull { item -> !item.isFolder }
	}
}
