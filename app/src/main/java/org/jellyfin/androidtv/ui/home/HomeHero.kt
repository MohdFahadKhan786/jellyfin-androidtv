package org.jellyfin.androidtv.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jellyfin.sdk.model.api.BaseItemDto

/**
 * First-pass home hero UI. Data and navigation are intentionally supplied by the caller
 * so this component does not depend on app-internal DI or SDK request construction.
 */
@Composable
fun HomeHero(
	item: BaseItemDto,
	onPlay: (BaseItemDto) -> Unit,
	onDetails: (BaseItemDto) -> Unit,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(32.dp),
		verticalArrangement = Arrangement.Bottom,
	) {
		org.jellyfin.androidtv.ui.base.Text(
			text = item.name.orEmpty(),
		)
		Spacer(Modifier.height(12.dp))
		Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
			androidx.compose.material3.Button(onClick = { onPlay(item) }) {
				org.jellyfin.androidtv.ui.base.Text("Play")
			}
			androidx.compose.material3.Button(onClick = { onDetails(item) }) {
				org.jellyfin.androidtv.ui.base.Text("More Info")
			}
		}
	}
}
