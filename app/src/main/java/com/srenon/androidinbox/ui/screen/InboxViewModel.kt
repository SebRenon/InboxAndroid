package com.srenon.androidinbox.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srenon.androidinbox.domain.usecase.FetchInboxUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InboxViewModel(
    private val fetchInboxUseCase: FetchInboxUseCase = FetchInboxUseCase,
    private val io: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    val inboxState = MutableStateFlow(InboxState())

    init {
        refreshInbox()
    }

    fun process(event: InboxViewEvent): Unit =
        when (event) {
            InboxViewEvent.OnRefresh -> refreshInbox()
            is InboxViewEvent.OnRequestActionClicked -> requestActionClicked(event.itemId)
        }

    private fun refreshInbox() {
        viewModelScope.launch(io) {
            inboxState.value = inboxState.value.copy(refreshing = true)
            val inboxItems = fetchInboxUseCase.run()
            inboxState.value = inboxState.value.copy(items = inboxItems, refreshing = false)
        }
    }

    private fun requestActionClicked(itemId: String) {
        val items = inboxState.value.items?.filter { it.id != itemId }
        inboxState.value = inboxState.value.copy(items = items)
    }
}
