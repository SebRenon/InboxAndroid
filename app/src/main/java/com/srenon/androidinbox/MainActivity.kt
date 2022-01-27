package com.srenon.androidinbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.srenon.androidinbox.ui.screen.InboxScreen
import com.srenon.androidinbox.ui.screen.InboxViewModel
import com.srenon.androidinbox.ui.theme.*

class MainActivity : ComponentActivity() {

    private val inboxViewModel by viewModels<InboxViewModel>()

    private val mainMenu = listOf(
        R.drawable.ic_icon_rx,
        R.drawable.ic_icon_calendar,
        R.drawable.ic_icon_payments,
        R.drawable.ic_icon_performance,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidInboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Home(inboxViewModel, mainMenu)
                }
            }
        }
    }
}

@Composable
fun Home(inboxViewModel: InboxViewModel, mainMenu: List<Int>) {
    Scaffold(
        topBar = { MainTopBar() },
        bottomBar = { MainBottomNav(mainMenu) }) {
        InboxScreen(inboxViewModel, it)
    }
}

@Composable
fun MainTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.appointments),
                color = if (isSystemInDarkTheme()) Color.White else Color(0xFF222222),
                fontWeight = W700
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_icon_open_tab),
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@Composable
fun MainBottomNav(mainMenu: List<Int>) {
    BottomNavigation {
        mainMenu.forEach {
            BottomNavigationItem(
                selected = it == R.drawable.ic_icon_calendar,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null
                    )
                },
                selectedContentColor = Purple,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = .3f),
            )
        }
    }
}
