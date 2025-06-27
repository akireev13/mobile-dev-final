package com.yourproblem.eatsmart.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(tableName = "meal_plans")
@TypeConverters(PlanConverters::class)
data class MealPlan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val startDate: String,
    val endDate: String,
    val days: List<MealDay>,
    val ingredients: List<Ingredient>?
) : Parcelable
