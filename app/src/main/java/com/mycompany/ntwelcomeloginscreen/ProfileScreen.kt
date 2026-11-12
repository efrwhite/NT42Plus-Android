package com.mycompany.ntwelcomeloginscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mycompany.ntwelcomeloginscreen.ui.theme.NTWelcomeLoginScreenTheme

/** Sample participant data for the UI preview; replace with account data when connected. */
data class ParticipantProfile(
    val name: String = "Anush Pradhan",
    val participantId: String = "N42-6767"
)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    participant: ParticipantProfile = ParticipantProfile(),
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val background = if (darkTheme) Color(0xFF211B1D) else Color(0xFFF7F0EA)
    val surface = if (darkTheme) Color(0xFF32292D) else Color.White
    val text = if (darkTheme) Color(0xFFE7B6C6) else Color(0xFF7D4057)
    val secondary = if (darkTheme) Color(0xFFB6BEC9) else Color(0xFF7B8A9E)
    val border = if (darkTheme) Color(0xFF986476) else Color(0xFFE9A8BC)
    var openSection by rememberSaveable { mutableStateOf<String?>(null) }
    val sections = listOf("Study week", "Community guidelines", "Contact study team", "Settings")

    Column(
        modifier.fillMaxSize().background(background).verticalScroll(rememberScrollState())
    ) {
        Column(Modifier.fillMaxWidth().padding(start = 36.dp, end = 24.dp, top = 36.dp, bottom = 12.dp)) {
            Text("Profile", fontSize = 32.sp, fontWeight = FontWeight.Medium, color = text)
            Text("Accounts & study info", fontSize = 14.sp, color = secondary)
        }
        HorizontalDivider(color = if (darkTheme) border else Color.White, thickness = 2.dp)
        Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 36.dp)) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = surface, shape = RoundedCornerShape(14.dp), shadowElevation = 5.dp
            ) {
                Column(
                    Modifier.padding(horizontal = 16.dp, vertical = 14.dp).heightIn(min = 146.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(78.dp), shape = CircleShape,
                        color = Color(0xFFF3A51F), border = BorderStroke(1.dp, Color(0xFF7D4057))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(participant.name.trim().take(1).uppercase(), color = Color.Black, fontSize = 34.sp)
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(participant.name, color = text, fontSize = 18.sp,
                        fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
                    Text("Participant ID : ${participant.participantId}", color = text,
                        fontSize = 14.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(18.dp))
                }
            }
            Spacer(Modifier.height(24.dp))
            Surface(modifier = Modifier.fillMaxWidth(), color = surface, shadowElevation = 4.dp) {
                Column(Modifier.border(1.dp, border)) {
                    sections.forEachIndexed { index, section ->
                        if (index > 0) HorizontalDivider(color = border, thickness = 1.dp)
                        Box(
                            Modifier.fillMaxWidth().clickable(role = Role.Button) { openSection = section }
                                .heightIn(min = 48.dp).padding(horizontal = 14.dp, vertical = 10.dp),
                            contentAlignment = Alignment.CenterStart
                        ) { Text(section, color = text, fontSize = 18.sp) }
                    }
                }
            }
        }
    }

    openSection?.let { section ->
        AlertDialog(
            onDismissRequest = { openSection = null },
            title = { Text(section) },
            text = {
                Text(when (section) {
                    "Study week" -> "Study progress and your current study week will appear here once study data is connected."
                    "Community guidelines" -> "The approved community guidelines will appear here once provided by the study team."
                    "Contact study team" -> "Study team contact details will appear here once provided."
                    else -> "Account settings will be available once accounts are connected."
                })
            },
            confirmButton = { TextButton(onClick = { openSection = null }) { Text("Close") } }
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 740)
@Composable
private fun ProfileLightPreview() {
    NTWelcomeLoginScreenTheme(darkTheme = false) { ProfileScreen(darkTheme = false) }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 740)
@Composable
private fun ProfileDarkPreview() {
    NTWelcomeLoginScreenTheme(darkTheme = true) { ProfileScreen(darkTheme = true) }
}
