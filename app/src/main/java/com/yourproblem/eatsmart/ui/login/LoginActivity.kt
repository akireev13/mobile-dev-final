package com.yourproblem.eatsmart.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.* // Import for Material 3 components
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yourproblem.eatsmart.ui.library.LibraryActivity
import kotlin.jvm.java
import kotlin.text.isNotBlank

@OptIn(ExperimentalMaterial3Api::class) // This annotation is for Material 3 components like OutlinedTextField
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = Firebase.auth

        // Check if user is already signed in. If so, navigate to MainActivity.
        if (auth.currentUser != null) {
            Log.d("LOGIN", "User already signed in. Navigating to MainActivity.")
            startActivity(Intent(this, LibraryActivity::class.java))
            finish() // Finish LoginActivity so user can't go back to it
        } else {
            // No user signed in, proceed to show the login UI
            enableEdgeToEdge() // Enables edge-to-edge display for modern Android UIs

            setContent {
                // We'll use a simple MaterialTheme for our app for now.
                // You might have your own custom theme set up.
                MaterialTheme {
                    // Our main login UI Composable
                    LoginScreen(
                        onSignInSuccess = {
                            // This callback is triggered when sign-in/sign-up is successful
                            Log.d("LOGIN", "Authentication successful. Navigating to MainActivity.")
                            startActivity(Intent(this@LoginActivity, LibraryActivity::class.java))
                            finish()
                        },
                        onSignInFailure = { errorMessage ->
                            // This callback is triggered when sign-in/sign-up fails
                            Log.d("LOGIN", "Authentication failed: $errorMessage")
                            Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSignInSuccess: () -> Unit,
    onSignInFailure: (String) -> Unit
) {
    // State variables to hold the email and password entered by the user
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = Firebase.auth

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to EatSmart!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        // Email Input Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Input Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(), // Hides password characters
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up Button
        Button(
            onClick = {
                // Here's where we call Firebase Authentication
                if (email.isNotBlank() && password.isNotBlank()) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign up success, call the success callback
                                onSignInSuccess()
                            } else {
                                // If sign up fails, display a message to the user.
                                val errorMessage = task.exception?.message ?: "Unknown authentication error"
                                onSignInFailure(errorMessage)
                            }
                        }
                } else {
                    onSignInFailure("Please enter both email and password.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        // You might want to add a "Sign In" button here if the user already has an account
        // and toggle between sign-up and sign-in modes.
        TextButton(
            onClick = {
                // Example: If you wanted a separate sign-in flow
                if (email.isNotBlank() && password.isNotBlank()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                onSignInSuccess()
                            } else {
                                val errorMessage = task.exception?.message ?: "Unknown sign-in error"
                                onSignInFailure(errorMessage)
                            }
                        }
                } else {
                    onSignInFailure("Please enter both email and password.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Sign In")
        }
    }
}

// A preview function to see your UI in Android Studio's design view
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        LoginScreen(
            onSignInSuccess = { /* Do nothing for preview */ },
            onSignInFailure = { /* Do nothing for preview */ }
        )
    }
}
