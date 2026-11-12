package com.mycompany.ntwelcomeloginscreen

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.n42.study.ReminderDetailScreen
import com.example.n42.study.RemindersScreen
import com.example.n42.study.TipDetailScreen
import com.example.n42.study.TipsScreen
import com.example.n42.study.getReminders
import com.example.n42.study.getTips
import com.mycompany.ntwelcomeloginscreen.ui.theme.NTWelcomeLoginScreenTheme

private val navigationPink = Color(0xFFE1A6B0)
private val navigationSelected = Color(0xFF983D4A)
private val pageCream = Color(0xFFF7F0EA)
private val pageText = Color(0xFF202B3E)

enum class AppTab(val label: String, @DrawableRes val icon: Int) {
    Profile("Profile", R.drawable.ic_nav_profile),
    Reminders("Reminders", R.drawable.ic_nav_reminders),
    Chat("Chat", R.drawable.ic_nav_chat),
    Tips("Tips", R.drawable.ic_nav_tips),
    Trivia("Trivia", R.drawable.ic_nav_trivia)
}

@Composable
fun MainNavigationScreen(onExit: () -> Unit) {
    var selectedName by rememberSaveable { mutableStateOf(AppTab.Profile.name) }
    var openItemId by rememberSaveable { mutableStateOf<Int?>(null) }
    val selected = AppTab.valueOf(selectedName)

    val reminder = if (selected == AppTab.Reminders && openItemId != null) {
        getReminders().find { it.id == openItemId }
    } else {
        null
    }
    val tip = if (selected == AppTab.Tips && openItemId != null) {
        getTips().find { it.id == openItemId }
    } else {
        null
    }

    BackHandler {
        when {
            openItemId != null -> openItemId = null
            selected != AppTab.Profile -> selectedName = AppTab.Profile.name
            else -> onExit()
        }
    }

    if (reminder != null) {
        ReminderDetailScreen(reminder = reminder, onBackClick = { openItemId = null })
        return
    }
    if (tip != null) {
        TipDetailScreen(tip = tip, onBackClick = { openItemId = null })
        return
    }

    Scaffold(
        containerColor = pageCream,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            AppNavigationBar(selectedTab = selected, onTabSelected = {
                openItemId = null
                selectedName = it.name
            })
        }
    ) { padding ->
        val contentModifier = Modifier.padding(padding)
        key(selected) {
            when (selected) {
                AppTab.Profile -> ProfileScreen(modifier = contentModifier)
                AppTab.Reminders -> RemindersScreen(
                    onReminderClick = { openItemId = it.id },
                    modifier = contentModifier
                )
                AppTab.Tips -> TipsScreen(
                    onTipClick = { openItemId = it.id },
                    modifier = contentModifier
                )
                AppTab.Chat -> PreviewPlaceholder(
                    title = selected.label,
                    message = "Chat will be available here once messaging is connected.",
                    onExit = onExit,
                    modifier = contentModifier
                )
                AppTab.Trivia -> PreviewPlaceholder(
                    title = selected.label,
                    message = "Trivia will be available here once questions are added.",
                    onExit = onExit,
                    modifier = contentModifier
                )
            }
        }
    }
}

@Composable
private fun PreviewPlaceholder(
    title: String,
    message: String,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextButton(onClick = onExit) { Text("Exit preview", color = navigationSelected) }
        Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = pageText)
        Text("App preview", color = navigationSelected, fontWeight = FontWeight.SemiBold)
        Text(message, color = pageText)
    }
}

@Composable
fun AppNavigationBar(selectedTab: AppTab, onTabSelected: (AppTab) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().selectableGroup()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        AppTab.entries.forEach { tab ->
            val selected = selectedTab == tab
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight()
                    .background(if (selected) navigationSelected else navigationPink)
                    .selectable(selected = selected, role = Role.Tab, onClick = { onTabSelected(tab) })
                    .heightIn(min = 104.dp).padding(horizontal = 2.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
            ) {
                Icon(
                    painterResource(tab.icon), contentDescription = null,
                    tint = if (selected) Color.White else Color.Black,
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    tab.label, color = Color.White, fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun MainNavigationPreview() {
    NTWelcomeLoginScreenTheme { MainNavigationScreen(onExit = {}) }
}

@Preview(showBackground = true, widthDp = 440)
@Composable
private fun RemindersNavigationPreview() {
    NTWelcomeLoginScreenTheme {
        AppNavigationBar(AppTab.Reminders, onTabSelected = {})
    }
}
