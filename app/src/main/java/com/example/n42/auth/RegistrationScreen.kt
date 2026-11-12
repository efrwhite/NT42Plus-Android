package com.example.n42.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable

/** NT & NT+ "Register" screen (Figma frame "NT 3"). No real account creation. */
@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit = {},
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    val firstError = submitted && firstName.isBlank()
    val lastError = submitted && lastName.isBlank()
    val emailMessage = if (submitted) emailProblem(email) else null
    val passError = submitted && password.length < 8
    val confirmError = submitted && confirm != password
    val formReady = firstName.isNotBlank() && lastName.isNotBlank() && isValidEmail(email) &&
            password.length >= 8 && confirm == password

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthTheme.Background)
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = AuthTheme.ScreenHorizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(Modifier.widthIn(max = AuthTheme.ContentMaxWidth).fillMaxWidth()) {
            Text(
                "← Back",
                style = AuthType.Link,
                modifier = Modifier.clickable(onClick = onBackClick).padding(vertical = 12.dp),
            )
            Spacer(Modifier.height(48.dp))
            Text("Register", style = AuthType.Title, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(49.dp))

            Column(verticalArrangement = Arrangement.spacedBy(AuthTheme.FieldGap)) {
                Row(horizontalArrangement = Arrangement.spacedBy(AuthTheme.FieldGap)) {
                    AuthTextField(
                        label = "First Name", value = firstName, onValueChange = { firstName = it },
                        placeholder = "John", modifier = Modifier.weight(1f),
                        isError = firstError, supportingText = if (firstError) "Required" else null,
                    )
                    AuthTextField(
                        label = "Last Name", value = lastName, onValueChange = { lastName = it },
                        placeholder = "Doe", modifier = Modifier.weight(1f),
                        isError = lastError, supportingText = if (lastError) "Required" else null,
                    )
                }
                AuthTextField(
                    label = "E-mail", value = email, onValueChange = { email = it },
                    placeholder = "Enter your email", keyboardType = KeyboardType.Email,
                    isError = emailMessage != null, supportingText = emailMessage,
                )
                AuthTextField(
                    label = "Password", value = password, onValueChange = { password = it },
                    placeholder = "*********", keyboardType = KeyboardType.Password, isPassword = true,
                    isError = passError, supportingText = "must contain 8 characters.",
                )
                AuthTextField(
                    label = "Confirm Password", value = confirm, onValueChange = { confirm = it },
                    placeholder = "*********", keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done, isPassword = true,
                    isError = confirmError, supportingText = if (confirmError) "Passwords do not match." else null,
                )
            }

            Spacer(Modifier.height(81.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                AuthPrimaryButton("Create Account", ready = formReady, onClick = {
                    submitted = true
                })
                if (submitted && formReady) {
                    Text("Demo only. Registration form validated.", style = AuthType.Body)
                }
                Text(
                    text = buildAnnotatedString {
                        append("By continuing, you agree to our ")
                        withStyle(SpanStyle(color = AuthTheme.Primary, textDecoration = TextDecoration.Underline)) {
                            append("Terms of Service")
                        }
                        append(" and ")
                        withStyle(SpanStyle(color = AuthTheme.Primary, textDecoration = TextDecoration.Underline)) {
                            append("Privacy Policy")
                        }
                        append(".")
                    },
                    style = AuthType.Body,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 411, heightDp = 891)
@Composable
private fun RegistrationPreview() = RegistrationScreen()
