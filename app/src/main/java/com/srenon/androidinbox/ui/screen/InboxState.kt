package com.srenon.androidinbox.ui.screen

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.srenon.androidinbox.R
import com.srenon.androidinbox.domain.model.InboxItem
import com.srenon.androidinbox.ui.theme.*

data class InboxState(
    val refreshing: Boolean = true,
    val items: List<InboxItem>? = null,
)

fun InboxItem.senderFullname() = with(sender) {
    "$firstName $lastName"
}

fun InboxItem.senderInitials() = with(sender) {
    "${firstName.first()}${lastName.first()}"
}

fun InboxItem.StartingSoon.formatParticipants() =
    participants.reduce { acc, s ->
        if (s == participants.last()) {
            "$acc and $s"
        } else {
            "$acc, $s"
        }
    }

@StringRes
fun InboxItem.label(): Int? = when (this) {
    is InboxItem.Request -> R.string.inbox_item_request_label
    is InboxItem.StartingSoon -> R.string.inbox_item_starting_soon_label
    is InboxItem.Unread,
    is InboxItem.Ended -> null
}


fun InboxItem.color(): Color = when (this) {
    is InboxItem.Request -> Purple6
    is InboxItem.StartingSoon -> Green9
    is InboxItem.Unread -> Yellow8
    is InboxItem.Ended -> Greyscale4
}
