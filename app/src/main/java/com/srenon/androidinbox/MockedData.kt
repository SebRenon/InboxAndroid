package com.srenon.androidinbox

import com.srenon.androidinbox.domain.model.InboxItem
import java.time.LocalDateTime


val mockedInboxItems
    get() = listOf(
        InboxItem.Request(
            id = "id_1",
            scheduledAt = LocalDateTime.now().plusDays(6).minusHours(4).plusMinutes(39),
            updatedAt = LocalDateTime.now(),
            sender = InboxItem.User(
                firstName = "Laura",
                lastName = "Skelvo",
            )
        ),
        InboxItem.StartingSoon(
            id = "id_2",
            updatedAt = LocalDateTime.now().minusMinutes(17),
            startTime = LocalDateTime.now().plusMinutes(15),
            sender = InboxItem.User(
                firstName = "Rumen",
                lastName = "Todorov",
            ),
            participants = listOf("Rumen", "River")
        ),
        InboxItem.Unread(
            id = "id_3",
            updatedAt = LocalDateTime.now().minusMinutes(16),
            message = "What do you think about trying one other than that might be a little different. What do you think about trying one other than that might be a little different",
            sender = InboxItem.User(
                firstName = "Joey",
                lastName = "Mavone",
            )
        ),
        InboxItem.Ended(
            id = "id_4",
            updatedAt = LocalDateTime.now().minusMinutes(38),
            sender = InboxItem.User(
                firstName = "Have",
                lastName = "Ditchings",
            )
        ),
        InboxItem.Ended(
            id = "id_5",
            updatedAt = LocalDateTime.now().minusMinutes(46),
            sender = InboxItem.User(
                firstName = "Cooper",
                lastName = "James",
            )
        ),
    )
