package com.srenon.androidinbox.domain.usecase

import com.srenon.androidinbox.domain.model.InboxItem
import com.srenon.androidinbox.domain.model.InboxItem.Request
import com.srenon.androidinbox.domain.model.InboxItem.StartingSoon
import com.srenon.androidinbox.mockedInboxItems
import kotlinx.coroutines.delay

object FetchInboxUseCase {
    suspend fun run(): List<InboxItem> {
        // simulate Http call
        delay(1_000)
        return mockedInboxItems.sortedWith { item1, item2 ->
            when {
                item1 is StartingSoon && item2 is StartingSoon ->
                    item1.startTime.compareTo(item2.startTime)
                item1.javaClass == item2.javaClass ->
                    item1.updatedAt.compareTo(item2.updatedAt)
                else ->
                    item1.priority().compareTo(item2.priority())
            }
        }
    }

    private fun InboxItem.priority() = when (this) {
        is StartingSoon -> 0
        is Request -> 1
        is InboxItem.Unread -> 2
        is InboxItem.Ended -> 3
    }
}
