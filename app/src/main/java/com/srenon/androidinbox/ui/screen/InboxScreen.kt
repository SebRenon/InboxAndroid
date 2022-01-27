package com.srenon.androidinbox.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.srenon.androidinbox.R
import com.srenon.androidinbox.domain.model.InboxItem
import com.srenon.androidinbox.ui.formatter.requestFormatter
import com.srenon.androidinbox.ui.formatter.timestampFormatter
import com.srenon.androidinbox.ui.theme.*

@Composable
fun InboxScreen(inboxViewModel: InboxViewModel, paddingValues: PaddingValues) {

    val state = inboxViewModel.inboxState.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(state.value.refreshing),
        onRefresh = { inboxViewModel.process(InboxViewEvent.OnRefresh) },
    ) {

        state.value.items?.let { items ->
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(items) { item ->
                    Item(item) { id ->
                        inboxViewModel.process(InboxViewEvent.OnRequestActionClicked(id))
                    }
                }
            }
        }

    }
}

@Composable
fun Item(item: InboxItem, onRequestActionClick: (id: String) -> Unit) {
    Box(Modifier.clickable { }) {
        Row(Modifier.padding(top = 16.dp)) {
            Row(
                Modifier.padding(start = 16.dp, end = 12.dp, top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Dot(item)
                Spacer(modifier = Modifier.width(8.dp))
                LeadingIcon(item)
            }
            Column(Modifier.padding(start = 8.dp)) {
                ItemHeader(item)
                Spacer(modifier = Modifier.height(4.dp))
                ItemContent(item)
                Spacer(modifier = Modifier.height(16.dp))
                ItemActions(item, onRequestActionClick)
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
            }
        }
    }
}

@Composable
fun Dot(item: InboxItem) {
    Box(
        Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(if (item is InboxItem.Ended) Color.Transparent else item.color())
    )
}

@Composable
fun ItemContent(item: InboxItem) {
    Text(
        text = item.text(),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = MaterialTheme.colors.onSurface.copy(alpha = .64f),
        fontWeight = W400,
        maxLines = if (item is InboxItem.Unread) 2 else Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(end = 54.dp)
    )
}

@Composable
fun LeadingIcon(item: InboxItem) {
    when (item) {
        is InboxItem.Request -> Image(
            painter = painterResource(id = R.drawable.ic_icon_request),
            contentDescription = "",
        )
        is InboxItem.StartingSoon -> Image(
            painter = painterResource(id = R.drawable.ic_icon_notification),
            contentDescription = ""
        )
        is InboxItem.Unread -> InitialIcon(item, Color.White)
        is InboxItem.Ended -> InitialIcon(item, Black.copy(alpha = .64f))
    }
}

@Composable
fun InitialIcon(item: InboxItem, textColor: Color) {
    Box(
        Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(item.color()),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = item.senderInitials(), fontSize = 12.sp, color = textColor, fontWeight = W700)
    }
}

@Composable
fun ItemActions(item: InboxItem, onRequestActionClick: (id: String) -> Unit) {
    when (item) {
        is InboxItem.Request -> RequestActions(item, onRequestActionClick)
        is InboxItem.StartingSoon -> StartingSoonActions(item)
        else -> {}
    }
}

@Composable
fun ItemHeader(item: InboxItem) {
    Row(
        Modifier
            .padding(end = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            item.label()?.let {
                Text(
                    text = stringResource(id = it),
                    fontSize = 12.sp,
                    color = item.color(),
                    fontWeight = W700,
                )
                Spacer(modifier = Modifier.height(2.dp))
            } ?: run {
                Spacer(modifier = Modifier.height(6.dp))
            }
            Text(
                text = item.senderFullname(),
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = W700,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.updatedAt.format(timestampFormatter).lowercase(),
            lineHeight = 20.sp,
            letterSpacing = .4.sp,
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 4.dp),
            color = if (isSystemInDarkTheme()) Greyscale7 else Greyscale10,
            fontWeight = W700,
        )
    }
}

@Composable
fun StartingSoonActions(item: InboxItem.StartingSoon) {
    Row {
        ActionButton(text = stringResource(R.string.join_appointment), bgColor = Green9) {}
    }
}

@Composable
fun RequestActions(item: InboxItem.Request, onClick: (id: String) -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(text = stringResource(R.string.accept), bgColor = Purple6) { onClick(item.id) }
        Spacer(modifier = Modifier.width(8.dp))
        ActionButton(
            text = stringResource(R.string.decline),
            textColor = Greyscale7,
            bgColor = Greyscale3,
        ) { onClick(item.id) }
    }
}

@Composable
fun ActionButton(
    text: String,
    textColor: Color = Color.White,
    bgColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .defaultMinSize(minHeight = 34.dp)
            .clip(RoundedCornerShape(40.dp))
            .clickable { onClick() }
            .background(bgColor)
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = W700
        )
    }
}

@Composable
fun InboxItem.text(): AnnotatedString =
    buildAnnotatedString {
        when (this@text) {
            is InboxItem.Ended -> append(stringResource(R.string.conversation_ended))
            is InboxItem.Request -> {
                append(stringResource(R.string.appointment_request, sender.firstName))
                withStyle(style = SpanStyle(fontWeight = W700)) {
                    append(scheduledAt.format(requestFormatter))
                }
            }
            is InboxItem.StartingSoon -> {
                append(
                    stringResource(
                        R.string.appointment_starting,
                        formatParticipants()
                    )
                )
            }
            is InboxItem.Unread -> append(message)
        }
    }
