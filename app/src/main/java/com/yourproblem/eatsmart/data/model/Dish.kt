// File: Dish.kt
package com.yourproblem.eatsmart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Dish(
    val name: String,
    val ingredients: List<String>
) : Parcelable
