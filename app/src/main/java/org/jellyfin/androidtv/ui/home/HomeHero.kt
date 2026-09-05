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
import org.jellyfin.androidtv.ui.base.Text
import org.jellyfin.androidtv.ui.base.button.Button
import org.jellyfin.sdk.model.api.BaseItemDto

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
		Text(text = item.name.orEmpty())
		Spacer(Modifier.height(12.dp))
		Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
			Button(onClick = { onPlay(item) }) { Text("Play") }
			Button(onClick = { onDetails(item) }) { Text("More Info") }
		}
	}
}
