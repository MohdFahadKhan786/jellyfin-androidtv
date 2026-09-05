package org.jellyfin.androidtv.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun NetflixBackdrop(
	modifier: Modifier = Modifier,
) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.background(
				Brush.verticalGradient(
					0f to Color.Transparent,
					0.55f to Color.Transparent,
					0.82f to Color.Black.copy(alpha = 0.82f),
					1f to Color.Black,
				)
			),
	)
}
