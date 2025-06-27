// File: MealDay.kt
package com.yourproblem.eatsmart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MealDay(
    val date: String,
    val scheduleDescription: String,
    val dishes: List<DishPortion>,
    val nutrients: DailyNutrients
) : Parcelable
