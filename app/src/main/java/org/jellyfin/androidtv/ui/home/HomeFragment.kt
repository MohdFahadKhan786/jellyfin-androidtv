package org.jellyfin.androidtv.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.fragment.app.Fragment
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.content
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.jellyfin.androidtv.auth.repository.ServerRepository
import org.jellyfin.androidtv.auth.repository.SessionRepository
import org.jellyfin.androidtv.data.repository.NotificationsRepository
import org.jellyfin.androidtv.ui.base.JellyfinTheme
import org.jellyfin.androidtv.ui.navigation.Destinations
import org.jellyfin.androidtv.ui.navigation.NavigationRepository
import org.jellyfin.androidtv.ui.playback.PlaybackLauncher
import org.jellyfin.androidtv.ui.shared.toolbar.MainToolbar
import org.jellyfin.androidtv.ui.shared.toolbar.MainToolbarActiveButton
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
	private val sessionRepository by inject<SessionRepository>()
	private val serverRepository by inject<ServerRepository>()
	private val notificationRepository by inject<NotificationsRepository>()
	private val navigationRepository by inject<NavigationRepository>()
	private val playbackLauncher by inject<PlaybackLauncher>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	) = content {
		val rowsFocusRequester = remember { FocusRequester() }
		var rowsSupportFragment by remember { mutableStateOf<HomeRowsFragment?>(null) }
		var heroItem by remember { mutableStateOf<HomeHeroData?>(null) }

		LaunchedEffect(rowsFocusRequester) { rowsFocusRequester.requestFocus() }

		JellyfinTheme {
			Column {
				MainToolbar(MainToolbarActiveButton.Home)

				heroItem?.let { hero ->
					HomeHero(
						item = hero.item,
						onPlay = { item -> playbackLauncher.launch(requireContext(), listOf(item)) },
						onDetails = { item -> navigationRepository.navigate(Destinations.itemDetails(item.id)) },
					)
				}

				AndroidFragment<HomeRowsFragment>(
					modifier = Modifier
						.focusGroup()
						.focusRequester(rowsFocusRequester)
						.focusProperties {
							onExit = {
								val isFirstRowSelected = rowsSupportFragment?.selectedPosition?.let { it <= 0 } ?: false
								if (requestedFocusDirection != FocusDirection.Up || !isFirstRowSelected) {
									cancelFocusChange()
								} else {
									rowsSupportFragment?.selectedPosition = 0
									rowsSupportFragment?.verticalGridView?.clearFocus()
								}
							}
						}
						.fillMaxSize(),
					onUpdate = { fragment ->
						rowsSupportFragment = fragment
						fragment.onHeroItemChanged = { item ->
							heroItem = item?.let(::HomeHeroData)
						}
					},
				)
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		sessionRepository.currentSession
			.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
			.map { session -> if (session == null) null else serverRepository.getServer(session.serverId) }
			.onEach { server -> notificationRepository.updateServerNotifications(server) }
			.launchIn(viewLifecycleOwner.lifecycleScope)
	}
}
