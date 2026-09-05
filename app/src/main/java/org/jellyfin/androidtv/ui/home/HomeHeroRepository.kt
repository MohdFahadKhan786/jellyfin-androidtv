package org.jellyfin.androidtv.ui.home

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
		val user = userRepository.currentUser.value ?: return null
		val request = GetLatestMediaRequest(
			userId = user.id,
			parentId = null,
			includeItemTypes = setOf(BaseItemKind.MOVIE, BaseItemKind.SERIES),
			fields = ItemRepository.browseFields,
			imageTypeLimit = 1,
			limit = 20,
			groupItems = false,
		)

		return api.get(request).content?.firstOrNull { item ->
			item.backdropImageTags?.isNotEmpty() == true
		}
	}
}
