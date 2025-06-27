package com.yourproblem.eatsmart.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yourproblem.eatsmart.data.model.MealPlan
import com.yourproblem.eatsmart.ui.theme.EatSmartTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val plan = intent.getParcelableExtra<MealPlan>("plan")
            ?: run { finish(); return }

        setContent {
            EatSmartTheme {
                DetailScreen(plan)
            }
        }
    }
}
