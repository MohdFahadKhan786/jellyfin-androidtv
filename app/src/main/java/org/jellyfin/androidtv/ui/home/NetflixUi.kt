package org.jellyfin.androidtv.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jellyfin.androidtv.ui.base.Text
import org.jellyfin.androidtv.ui.base.button.Button
import org.jellyfin.androidtv.ui.base.button.ButtonDefaults

@Composable
fun NetflixTopNav(
    active: NetflixNavItem = NetflixNavItem.Home,
    onNavigate: (NetflixNavItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(76.dp)
            .background(Color.Black.copy(alpha = 0.96f)),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetflixNavButton(NetflixNavItem.Home, active, onNavigate)
        NetflixNavButton(NetflixNavItem.TVShows, active, onNavigate)
        NetflixNavButton(NetflixNavItem.Movies, active, onNavigate)
        NetflixNavButton(NetflixNavItem.NewPopular, active, onNavigate)
    }
}

@Composable
private fun NetflixNavButton(
    item: NetflixNavItem,
    active: NetflixNavItem,
    onNavigate: (NetflixNavItem) -> Unit,
) {
    Button(
        onClick = { onNavigate(item) },
        colors = ButtonDefaults.colors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            focusedContainerColor = Color.White.copy(alpha = 0.08f),
            focusedContentColor = Color.White,
        ),
        contentPadding = ButtonDefaults.ContentPadding,
    ) {
        Text(
            text = item.label,
            color = if (item == active) {
                Color.White
            } else {
                Color.White.copy(alpha = 0.78f)
            },
        )
    }
}

enum class NetflixNavItem(val label: String) {
    Home("Home"),
    TVShows("TV Shows"),
    Movies("Movies"),
    NewPopular("New & Popular"),
}
