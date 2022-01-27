package com.srenon.androidinbox.ui.screen

sealed class InboxViewEvent {
    object OnRefresh : InboxViewEvent()
    class OnRequestActionClicked(val itemId: String) : InboxViewEvent()
}
