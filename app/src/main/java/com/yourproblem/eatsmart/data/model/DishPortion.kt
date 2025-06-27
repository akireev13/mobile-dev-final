// File: DishPortion.kt
package com.yourproblem.eatsmart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class DishPortion(
    val dish: Dish,
    val portions: Int
) : Parcelable
