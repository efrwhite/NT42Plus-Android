package com.mycompany.ntwelcomeloginscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mycompany.ntwelcomeloginscreen.ui.theme.NTWelcomeLoginScreenTheme
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun profileMenuOpensAndClosesEachSection() {
        compose.setContent { NTWelcomeLoginScreenTheme { ProfileScreen() } }
        listOf("Study week", "Community guidelines", "Contact study team", "Settings").forEach { section ->
            compose.onNodeWithText(section).performClick()
            compose.onNodeWithText("Close").assertIsDisplayed().performClick()
            compose.onNodeWithText("Participant ID : N42-6767").assertIsDisplayed()
        }
    }

    @Test
    fun previewOpensEveryDestinationAndReturnsToWelcome() {
        compose.setContent { NTWelcomeLoginScreenTheme { WelcomeLoginApp() } }
        compose.onNodeWithText("Preview app").performClick()
        AppTab.entries.forEach { tab ->
            compose.onNode(hasText(tab.label) and isSelectable()).performClick().assertIsSelected()
            val message = when (tab) {
                AppTab.Profile -> "Anush Pradhan"
                AppTab.Reminders -> "Sent by the study team"
                AppTab.Chat -> "Chat will be available here once messaging is connected."
                AppTab.Tips -> "Weekly Tips"
                AppTab.Trivia -> "Trivia will be available here once questions are added."
            }
            compose.onNodeWithText(message).assertIsDisplayed()
        }
        compose.onNodeWithText("Exit preview").performClick()
        compose.onNodeWithText("Preview app").assertIsDisplayed()
    }

    @Test
    fun reminderAndRecipeDetailsReturnToTheirLists() {
        compose.setContent { NTWelcomeLoginScreenTheme { WelcomeLoginApp() } }
        compose.onNodeWithText("Preview app").performClick()

        compose.onNode(hasText("Reminders") and isSelectable()).performClick()
        compose.onNodeWithText("Weekly check-in is open").performClick()
        compose.onNodeWithText("When").assertIsDisplayed()
        compose.onNodeWithText("← Back").performClick()
        compose.onNode(hasText("Reminders") and isSelectable()).assertIsSelected()
        compose.onNodeWithText("Weekly check-in is open").assertIsDisplayed()

        compose.onNode(hasText("Tips") and isSelectable()).performClick()
        compose.onNodeWithText("Overnight oats").performClick()
        compose.onNodeWithText("Ingredients").assertIsDisplayed()
        compose.onNodeWithText("← Back").performClick()
        compose.onNode(hasText("Tips") and isSelectable()).assertIsSelected()
        compose.onNodeWithText("Weekly Tips").assertIsDisplayed()
    }
}
