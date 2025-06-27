// File: PlanConverters.kt
package com.yourproblem.eatsmart.data.model

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PlanConverters {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val listType = Types.newParameterizedType(List::class.java, MealDay::class.java)
    private val ingredientList = Types.newParameterizedType(List::class.java, Ingredient::class.java)

    private val dayAdapter = moshi.adapter<List<MealDay>>(listType)
    private val ingredientAdapter = moshi.adapter<List<Ingredient>>(ingredientList)

    @TypeConverter
    fun daysToJson(value: List<MealDay>): String = dayAdapter.toJson(value)

    @TypeConverter
    fun jsonToDays(value: String): List<MealDay> = dayAdapter.fromJson(value) ?: emptyList()

    @TypeConverter
    fun ingredientsToJson(value: List<Ingredient>): String = ingredientAdapter.toJson(value)

    @TypeConverter
    fun jsonToIngredients(value: String): List<Ingredient> =
        ingredientAdapter.fromJson(value) ?: emptyList()
}
