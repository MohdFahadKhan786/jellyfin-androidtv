package org.jellyfin.androidtv.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
	Box(
		modifier = modifier
			.fillMaxWidth()
			.height(320.dp)
			.background(
				Brush.verticalGradient(
					listOf(Color.Transparent, Color.Black.copy(alpha = 0.96f))
				)
			),
	) {
		Column(
			modifier = Modifier
				.align(Alignment.BottomStart)
				.padding(start = 36.dp, end = 36.dp, bottom = 28.dp)
				.width(560.dp),
		) {
			Text(
				text = item.name.orEmpty(),
				fontWeight = FontWeight.Bold,
				color = Color.White,
			)
			Spacer(Modifier.height(10.dp))
			Text(
				text = buildString {
					item.productionYear?.let { append(it).append("  •  ") }
					item.genres?.take(3)?.joinToString("  •  ")?.let(::append)
				},
				color = Color.White.copy(alpha = 0.88f),
			)
			Spacer(Modifier.height(16.dp))
			Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
				Button(
					onClick = { onPlay(item) },
					shape = RoundedCornerShape(24.dp),
				) { Text("Play") }
				Button(
					onClick = { onDetails(item) },
					shape = RoundedCornerShape(24.dp),
				) { Text("More Info") }
			}
		}
	}
}
