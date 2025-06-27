package com.yourproblem.eatsmart.network

import com.yourproblem.eatsmart.data.model.MealPlan

interface MealPlanResult {
    fun onSuccessGeneratePlan(plan: MealPlan)

    fun onFailGeneratePlan()

}