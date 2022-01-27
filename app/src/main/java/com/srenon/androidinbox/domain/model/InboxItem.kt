package com.srenon.androidinbox.domain.model

import java.time.LocalDateTime

sealed class InboxItem(
    open val id: String,
    open val updatedAt: LocalDateTime,
    open val sender: User
) {
    class Request(
        id: String,
        updatedAt: LocalDateTime,
        val scheduledAt: LocalDateTime,
        sender: User,
    ) : InboxItem(id, updatedAt, sender)

    class StartingSoon(
        id: String,
        val startTime: LocalDateTime,
        updatedAt: LocalDateTime,
        sender: User,
        val participants: List<String>,
    ) : InboxItem(id, updatedAt, sender)

    class Unread(
        id: String,
        val message: String,
        updatedAt: LocalDateTime,
        sender: User,
    ) : InboxItem(id, updatedAt, sender)

    class Ended(
        id: String,
        updatedAt: LocalDateTime,
        sender: User,
    ) : InboxItem(id, updatedAt, sender)

    data class User(
        val firstName: String,
        val lastName: String,
    )
}
