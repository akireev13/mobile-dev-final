package com.yourproblem.eatsmart.ui.library

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yourproblem.eatsmart.ui.detail.DetailActivity
import com.yourproblem.eatsmart.ui.generate.GenerateActivity
import com.yourproblem.eatsmart.ui.login.LoginActivity
import com.yourproblem.eatsmart.ui.theme.EatSmartTheme

class LibraryActivity : ComponentActivity() {
    private val vm: LibraryViewModel by viewModels()




    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {



            val context = LocalContext.current
            val auth = Firebase.auth
            val onSignOut: () -> Unit = {
                auth.signOut() // Sign out the user from Firebase
                Log.d("MAIN_ACTIVITY", "User signed out.")

                // Navigate back to LoginActivity
                val intent = Intent(context, LoginActivity::class.java).apply {
                    // Clear the activity stack so the user can't go back to MainActivity
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)

                // Optional: Show a toast message
                Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
            }
            EatSmartTheme {
                LibraryScreen(

                    onPlanSelected = { plan ->
                        startActivity(
                            Intent(this, DetailActivity::class.java)
                                .putExtra("plan", plan)
                        )
                    },
                    onGenerate = {
                        startActivity(
                            Intent(this, GenerateActivity::class.java)
                        )
                    },
                    viewModel = vm,
                    onSignOut = onSignOut
                )


            }
            }
        }
    }
