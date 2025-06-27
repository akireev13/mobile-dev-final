// File: DailyNutrients.kt
package com.yourproblem.eatsmart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class DailyNutrients(
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int
) : Parcelable
