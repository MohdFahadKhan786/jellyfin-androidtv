package org.jellyfin.androidtv.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jellyfin.sdk.model.api.BaseItemDto

@Composable
fun HomeHeroPreview(
	item: BaseItemDto,
	onPlay: (BaseItemDto) -> Unit,
	onDetails: (BaseItemDto) -> Unit,
	modifier: Modifier = Modifier,
) {
	HomeHero(
		item = item,
		onPlay = onPlay,
		onDetails = onDetails,
		modifier = modifier,
	)
}
