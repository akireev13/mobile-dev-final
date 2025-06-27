package com.yourproblem.eatsmart.ui.generate

import com.yourproblem.eatsmart.data.model.MealPlan

sealed class GenerateState {
    object Idle : GenerateState()
    object Loading : GenerateState()
    data class Success(val plan: MealPlan) : GenerateState()
    data class Error(val msg: String) : GenerateState()
}
