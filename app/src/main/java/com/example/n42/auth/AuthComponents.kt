package com.example.n42.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



/** Tokens taken from the NT & NT+ frames (NT 1 to NT 3) and the "NT & NT+ Palette" in the Figma file. */
object AuthTheme {
    // NT & NT+ palette: the only colors used in the app. Names describe the swatch.
    private val Rose = Color(0xFFB9707A)       // B9707A
    private val RoseDeep = Color(0xFF97404C)   // deep rose swatch (unlabeled in Figma, Rectangle 9)
    private val Blush = Color(0xFFF7E4E6)      // F7E4E6
    private val Cream = Color(0xFFF7F0EA)      // F7F0EA
    private val Ivory = Color(0xFFFFFBF6)      // FFFBF6
    private val Cocoa = Color(0xFF3B2E2A)      // 3B2E2A
    private val Taupe = Color(0xFF9C8A7E)      // 9C8A7E
    private val Rust = Color(0xFFB3492F)       // B3492F
    // D9A441 (gold) is in the palette but not used on the auth screens

    val Background = Cream                     // screen background
    val Primary = Rose                         // button, links, focus border
    val PrimaryDeep = RoseDeep

    val ButtonDefault = Primary                // resting button color
    val ButtonReady = PrimaryDeep              // button color once the form is valid
    val OnPrimary = Ivory                      // button text

    // Text
    val Title = Cocoa
    val Label = Cocoa
    val Body = Cocoa                           // terms text
    val InputText = Cocoa
    val Placeholder = Taupe
    val Supporting = Taupe                     // helper text under a field
    val Icon = Cocoa                           // password eye icon

    // Input
    val InputFill = Ivory
    val InputBorder = Blush
    val InputBorderPassword = Taupe

    // Status
    val Error = Rust
    val Success = Cocoa                        // palette has no green, so the confirmation uses dark text

    // Layout (dp)
    val ContentMaxWidth = 358.dp
    val ScreenHorizontalPadding = 24.dp
    val FieldGap = 12.dp
    val LabelGap = 6.dp
    val InputHeight = 46.dp
    val InputRadius = 8.dp
    val InputBorderWidth = 1.5.dp
    val ButtonHeight = 44.dp
    val IconSize = 20.dp
}

object AuthType {
    val Title = TextStyle(
        fontWeight = FontWeight.Bold, fontSize = 30.sp,
        lineHeight = 34.sp, letterSpacing = (-0.15).sp, color = AuthTheme.Title, textAlign = TextAlign.Center,
    )
    val Label = TextStyle(
        fontWeight = FontWeight.Normal, fontSize = 14.sp,
        lineHeight = 22.sp, color = AuthTheme.Label,
    )
    val Input = TextStyle(
        fontWeight = FontWeight.Normal, fontSize = 14.sp,
        lineHeight = 22.sp, color = AuthTheme.InputText,
    )
    val Body = TextStyle(
        fontWeight = FontWeight.Normal, fontSize = 14.sp,
        lineHeight = 22.sp, letterSpacing = 0.14.sp, color = AuthTheme.Body, textAlign = TextAlign.Center,
    )
    val Button = TextStyle(
        fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp,
    )
    val Link = TextStyle(
        fontWeight = FontWeight.Medium, fontSize = 14.sp,
        lineHeight = 22.sp, letterSpacing = 0.175.sp, color = AuthTheme.Primary,
    )
}

enum class SupportingType { Default, Error, Success }

@Composable
fun AuthTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null,
    supportingType: SupportingType = if (isError) SupportingType.Error else SupportingType.Default,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var focused by remember { mutableStateOf(false) }

    val borderColor = when {
        isError -> AuthTheme.Error
        focused -> AuthTheme.Primary
        isPassword -> AuthTheme.InputBorderPassword
        else -> AuthTheme.InputBorder
    }
    val shape = RoundedCornerShape(AuthTheme.InputRadius)

    Column(modifier, verticalArrangement = Arrangement.spacedBy(AuthTheme.LabelGap)) {
        Text(label, style = AuthType.Label)

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = AuthType.Input,
            cursorBrush = SolidColor(AuthTheme.Primary),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            visualTransformation =
                if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .height(AuthTheme.InputHeight)
                .onFocusChanged { focused = it.isFocused }
                .background(AuthTheme.InputFill, shape)
                .border(BorderStroke(AuthTheme.InputBorderWidth, borderColor), shape),
            decorationBox = { inner ->
                Row(
                    Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty()) {
                            Text(placeholder, style = AuthType.Input.copy(color = AuthTheme.Placeholder), maxLines = 1)
                        }
                        inner()
                    }
                    if (isPassword) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = AuthTheme.Icon,
                            modifier = Modifier
                                .size(AuthTheme.IconSize)
                                .clickable { passwordVisible = !passwordVisible },
                        )
                    }
                }
            },
        )

        if (supportingText != null) {
            val color = when (supportingType) {
                SupportingType.Default -> AuthTheme.Supporting
                SupportingType.Error -> AuthTheme.Error
                SupportingType.Success -> AuthTheme.Success
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                if (supportingType == SupportingType.Error) {
                    Icon(Icons.Outlined.Info, contentDescription = null, tint = color, modifier = Modifier.size(AuthTheme.IconSize))
                }
                Text(supportingText, style = AuthType.Label.copy(color = color))
            }
        }
    }
}

@Composable
fun AuthPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    ready: Boolean = false,
) {
    val container by animateColorAsState(
        targetValue = if (ready) AuthTheme.ButtonReady else AuthTheme.ButtonDefault,
        label = "buttonColor",
    )
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(percent = 50), // pill shape, as in the NT frames
        colors = ButtonDefaults.buttonColors(containerColor = container, contentColor = AuthTheme.OnPrimary),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
        modifier = modifier.fillMaxWidth().height(AuthTheme.ButtonHeight),
    ) {
        Text(text, style = AuthType.Button)
    }
}

private val EMAIL_REGEX =
    Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$")

private val DOMAIN_TYPOS = mapOf(
    "gail.com" to "gmail.com", "gmial.com" to "gmail.com", "gmal.com" to "gmail.com",
    "gmai.com" to "gmail.com", "gmil.com" to "gmail.com", "gamil.com" to "gmail.com",
    "gnail.com" to "gmail.com", "gmail.co" to "gmail.com", "gmail.con" to "gmail.com",
    "yaho.com" to "yahoo.com", "yahoo.con" to "yahoo.com", "hotmial.com" to "hotmail.com",
    "hotmal.com" to "hotmail.com", "outlok.com" to "outlook.com", "iclod.com" to "icloud.com",
)

/** Returns an error message, or null if the email looks OK. */
fun emailProblem(email: String): String? {
    val e = email.trim()
    if (!EMAIL_REGEX.matches(e)) return "Enter a valid email address"
    val suggestion = DOMAIN_TYPOS[e.substringAfter('@').lowercase()]
    if (suggestion != null) return "Did you mean ${e.substringBefore('@')}@$suggestion?"
    return null
}

fun isValidEmail(email: String): Boolean = emailProblem(email) == null
