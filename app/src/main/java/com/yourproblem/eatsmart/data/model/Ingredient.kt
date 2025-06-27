package com.yourproblem.eatsmart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val name: String,
    val quantity: String
) : Parcelable
