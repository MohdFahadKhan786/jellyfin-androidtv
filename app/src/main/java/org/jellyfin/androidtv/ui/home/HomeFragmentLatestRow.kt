package org.jellyfin.androidtv.ui.home

import android.content.Context
import androidx.leanback.widget.Row
import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.auth.repository.UserRepository
import org.jellyfin.androidtv.constant.ChangeTriggerType
import org.jellyfin.androidtv.constant.ImageType
import org.jellyfin.androidtv.data.repository.ItemRepository
import org.jellyfin.androidtv.ui.browsing.BrowseRowDef
import org.jellyfin.androidtv.ui.presentation.CardPresenter
import org.jellyfin.androidtv.ui.presentation.MutableObjectAdapter
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.request.GetLatestMediaRequest

/**
 * ddflix: unified "Recently added" row spanning ALL libraries (Movies, TV, Anime, etc)
 * in one row, instead of a separate "Latest in X" row per library.
 *
 * Uses GetLatestMediaRequest with parentId omitted — confirmed via the Jellyfin SDK
 * docs that omitting parentId queries across the root (all libraries), while
 * groupItems still groups episodes under their series card. This is the same
 * endpoint the per-library rows used, just without the per-library scoping.
 *
 * NOTE: the previous per-library version respected each user's server-side
 * "exclude from Latest" checkbox per library (userViews / latestItemsExcludes).
 * That per-library exclude preference can't be applied client-side anymore since
 * we're not iterating libraries individually — we rely on includeItemTypes to
 * keep playlists/boxsets/live-tv/books out, same practical effect for most
 * setups, but if you specifically excluded one library from Latest before,
 * that exclusion may not carry over. Flagging this so it doesn't surprise you.
 */
class HomeFragmentLatestRow(
	private val userRepository: UserRepository,
	private val userViews: Collection<BaseItemDto>,
) : HomeFragmentRow {
	private val heroCardPresenter = CardPresenter(true, ImageType.THUMB, HERO_CARD_HEIGHT)

	override fun addToRowsAdapter(context: Context, cardPresenter: CardPresenter, rowsAdapter: MutableObjectAdapter<Row>) {
		val request = GetLatestMediaRequest(
			fields = ItemRepository.browseFields,
			imageTypeLimit = 1,
			parentId = null, // omitted on purpose -> queries across all libraries
			includeItemTypes = INCLUDED_ITEM_TYPES,
			groupItems = true,
			limit = ITEM_LIMIT,
		)

		val title = context.getString(R.string.lbl_latest)
		val row = HomeFragmentBrowseRowDefRow(BrowseRowDef(title, request, arrayOf(ChangeTriggerType.LibraryUpdated)))
		row.addToRowsAdapter(context, heroCardPresenter, rowsAdapter)
	}

	companion object {
		// Restricts the unified row to actual watchable movie/TV content —
		// keeps playlists, boxsets, live-tv recordings, books, and music out
		// without needing per-library collectionType checks anymore.
		private val INCLUDED_ITEM_TYPES = setOf(
			BaseItemKind.MOVIE,
			BaseItemKind.SERIES,
			BaseItemKind.EPISODE,
		)

		private const val ITEM_LIMIT = 50

		private const val HERO_CARD_HEIGHT = 220
	}
}
