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
fun NetflixHero(
    item: BaseItemDto,
    onPlay: (BaseItemDto) -> Unit,
    onDetails: (BaseItemDto) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(430.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.86f),
                        Color.Black,
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 52.dp, end = 52.dp, bottom = 34.dp)
                .width(620.dp),
        ) {
            Text(
                text = item.name.orEmpty(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = buildString {
                    item.productionYear?.let { append(it) }
                    item.officialRating?.let {
                        if (isNotEmpty()) append("  •  ")
                        append(it)
                    }
                    item.genres?.take(3)?.joinToString("  •  ")?.let {
                        if (isNotEmpty()) append("  •  ")
                        append(it)
                    }
                },
                color = Color.White.copy(alpha = 0.9f),
            )
            Spacer(Modifier.height(18.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
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
