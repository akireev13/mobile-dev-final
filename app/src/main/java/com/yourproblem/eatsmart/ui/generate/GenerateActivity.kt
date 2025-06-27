package com.yourproblem.eatsmart.ui.generate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.yourproblem.eatsmart.ui.detail.DetailActivity
import com.yourproblem.eatsmart.ui.theme.EatSmartTheme

class GenerateActivity : ComponentActivity() {
    private val vm: GenerateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EatSmartTheme {
                GenerateScreen(
                    viewModel = vm,
                    onCompleted = { plan ->
                        startActivity(
                            Intent(this, DetailActivity::class.java)
                                .putExtra("plan", plan)
                        )
                        finish()  // optional: close generator screen
                    },
                    onCancel = { finish() }
                )
            }
        }
    }
}
