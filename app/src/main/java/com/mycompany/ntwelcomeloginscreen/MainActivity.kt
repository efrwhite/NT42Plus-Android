package com.mycompany.ntwelcomeloginscreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.n42.auth.ForgotPasswordScreen
import com.example.n42.auth.RegistrationScreen
import com.mycompany.ntwelcomeloginscreen.ui.theme.NTWelcomeLoginScreenTheme

// Approximate colors from the Figma screenshot.
private val cream = Color(0xFFF7F0EA)
private val green = Color(0xFF278B66)
private val burgundy = Color(0xFFA43F55)
private val pink = Color(0xFFCD7181)
private val darkText = Color(0xFF202B3E)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NTWelcomeLoginScreenTheme {
                WelcomeLoginApp()
            }
        }
    }
}

@Composable
fun WelcomeLoginApp() {
    var screen by rememberSaveable { mutableStateOf("welcome") }

    BackHandler(enabled = screen != "welcome") {
        screen = if (screen == "forgotPassword") "login" else "welcome"
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = cream
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            when (screen) {
                "welcome" -> WelcomeScreen(
                    onLoginClick = { screen = "login" },
                    onRegisterClick = { screen = "register" },
                    onPreviewClick = { screen = "demo" }
                )

                "login" -> LoginScreen(
                    onBackClick = { screen = "welcome" },
                    onForgotPasswordClick = { screen = "forgotPassword" }
                )

                "forgotPassword" -> ForgotPasswordScreen(
                    onBackToLogin = { screen = "login" }
                )

                "demo" -> MainNavigationScreen(onExit = { screen = "welcome" })

                "register" -> RegistrationScreen(
                    onBackClick = { screen = "welcome" }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPreviewClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.drawable.nt_logo),
            contentDescription = "NT+ logo",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.weight(3f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onLoginClick,
                modifier = Modifier.heightIn(min = 48.dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, darkText),
                colors = ButtonDefaults.buttonColors(
                    containerColor = green,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 12.dp
                )
            ) {
                Text("Login")
            }

            GreenButton(
                text = "Register",
                onClick = onRegisterClick
            )
        }

        TextButton(
            onClick = onPreviewClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Preview app", color = burgundy)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text("← Back", color = darkText)
        }

        Spacer(modifier = Modifier.height(96.dp))

        Text(
            text = "Login",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = darkText,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        FieldLabel("Email address")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter your email") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            colors = loginFieldColors()
        )

        Spacer(modifier = Modifier.height(16.dp))

        FieldLabel("Password")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter your password") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Text(
                        text = if (passwordVisible) "Hide" else "Show",
                        color = green
                    )
                }
            },
            colors = loginFieldColors()
        )

        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Forgot Password?", color = pink)
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = burgundy,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        GreenButton(
            text = "Login",
            onClick = {
                when {
                    email.isBlank() || password.isBlank() -> {
                        errorMessage =
                            "Please enter your email and password."
                    }

                    !android.util.Patterns.EMAIL_ADDRESS
                        .matcher(email.trim()).matches() -> {
                        errorMessage = "Please enter a valid email address."
                    }

                    else -> {
                        Toast.makeText(
                            context,
                            "Login is not connected to authentication yet.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(green)
            )

            Text("Or", color = darkText, fontSize = 14.sp)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(green)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        listOf("Apple", "Google", "Facebook").forEach { provider ->
            OutlinedButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "$provider login is not connected yet.",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .heightIn(min = 48.dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, pink),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = darkText
                )
            ) {
                Text("Continue with $provider")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun FieldLabel(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = darkText,
            fontSize = 14.sp
        )

        Text(
            text = "*",
            color = burgundy,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = green,
    unfocusedBorderColor = Color(0xFFD8E0EB),
    focusedTextColor = darkText,
    unfocusedTextColor = darkText,
    cursorColor = green
)

@Composable
fun GreenButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.heightIn(min = 48.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = green,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 12.dp
        )
    ) {
        Text(text)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun WelcomePreview() {
    NTWelcomeLoginScreenTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = cream
        ) {
            WelcomeScreen(
                onLoginClick = {},
                onRegisterClick = {}
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LoginPreview() {
    NTWelcomeLoginScreenTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = cream
        ) {
            LoginScreen(onBackClick = {})
        }
    }
}
