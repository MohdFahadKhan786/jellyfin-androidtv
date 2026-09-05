package org.jellyfin.androidtv.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jellyfin.sdk.model.api.BaseItemDto

class HomeHeroViewModel(
	private val repository: HomeHeroRepository,
) : ViewModel() {
	private val _item = MutableStateFlow<BaseItemDto?>(null)
	val item: StateFlow<BaseItemDto?> = _item.asStateFlow()

	init {
		refresh()
	}

	fun refresh() {
		viewModelScope.launch {
			_item.value = runCatching { repository.getFeaturedItem() }.getOrNull()
		}
	}
}
