package com.yourproblem.eatsmart

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.yourproblem.eatsmart.data.model.*
import com.yourproblem.eatsmart.ui.detail.DetailScreen
import com.yourproblem.eatsmart.ui.theme.EatSmartTheme
import org.junit.Rule
import org.junit.Test

class DetailScreenCollapseTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val dummyPlan = MealPlan(
        title = "Dummy",
        startDate = "2025-01-01",
        endDate = "2025-01-07",
        days = listOf(
            MealDay(
                date = "2025-01-01",
                scheduleDescription = "Three meals",
                dishes = emptyList(),
                nutrients = DailyNutrients(2000, 150, 80, 180)
            )
        ),
        ingredients = listOf(
            Ingredient("beef", "1 kg"),
            Ingredient("eggs", "12 pcs")
        )
    )

    @Test
    fun groceryList_collapsesAndExpands() {
        composeRule.setContent {
            EatSmartTheme { DetailScreen(dummyPlan) }
        }

        composeRule.onNodeWithText("beef").assertExists()
        composeRule.onNodeWithText("Grocery list").performClick()
        composeRule.onNodeWithText("beef").assertDoesNotExist()
        composeRule.onNodeWithText("Grocery list").performClick()
        composeRule.onNodeWithText("beef").assertExists()
    }
}
