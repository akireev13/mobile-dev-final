package com.yourproblem.eatsmart.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealPlanRequest(
    val gender: String,
    val weight: Float,
    val diet: String
)