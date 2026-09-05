package org.jellyfin.androidtv.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jellyfin.androidtv.ui.base.JellyfinTheme
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
            .background(Color.Black.copy(alpha = 0.96f))
            .padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetflixNavButton(NetflixNavItem.Home, active, onNavigate)
        NetflixNavButton(NetflixNavItem.TVShows, active, onNavigate)
        NetflixNavButton(NetflixNavItem.Movies, active, onNavigate)
        NetflixNavButton(NetflixNavItem.NewPopular, active, onNavigate)
    }
}

private fun RowScope.NetflixNavButton(
    item: NetflixNavItem,
    active: NetflixNavItem,
    onNavigate: (NetflixNavItem) -> Unit,
) {
    Button(
        onClick = { onNavigate(item) },
        colors = if (item == active) {
            ButtonDefaults.colors(
                containerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            )
        } else {
            ButtonDefaults.colors(
                containerColor = Color.Transparent,
                focusedContainerColor = Color.White.copy(alpha = 0.08f),
            )
        },
        contentPadding = ButtonDefaults.ContentPadding.copy(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(
            text = item.label,
            color = if (item == active) Color.White else JellyfinTheme.colorScheme.onBackground.copy(alpha = 0.78f),
        )
    }
}

enum class NetflixNavItem(val label: String) {
    Home("Home"),
    TVShows("TV Shows"),
    Movies("Movies"),
    NewPopular("New & Popular"),
}
