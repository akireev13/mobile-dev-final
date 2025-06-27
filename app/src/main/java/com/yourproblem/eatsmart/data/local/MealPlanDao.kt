package com.yourproblem.eatsmart.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourproblem.eatsmart.data.model.MealPlan

@Dao
interface MealPlanDao {
    @Query("SELECT * FROM meal_plans ORDER BY startDate DESC")
    fun getAll(): LiveData<List<MealPlan>>

    @Query("SELECT * FROM meal_plans WHERE id = :id")
    fun getById(id: Long): MealPlan

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(plan: MealPlan): Long

    @Delete
    fun delete(plan: MealPlan)
}
