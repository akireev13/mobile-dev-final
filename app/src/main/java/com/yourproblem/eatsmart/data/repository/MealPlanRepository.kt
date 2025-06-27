package com.yourproblem.eatsmart.data.repository

import androidx.lifecycle.LiveData
import com.yourproblem.eatsmart.data.local.MealPlanDao
import com.yourproblem.eatsmart.data.local.MealPlanDatabase
import com.yourproblem.eatsmart.data.model.MealPlan
import java.util.concurrent.Executors

class MealPlanRepository(private val dao: MealPlanDao) {

    val plans: LiveData<List<MealPlan>> = dao.getAll()

    fun getPlan(id: Long): MealPlan = dao.getById(id)

    fun insert(plan: MealPlan) {
        MealPlanDatabase.databaseWriteExecutor.execute { dao.insert(plan) }
    }

    fun delete(plan: MealPlan) {
        MealPlanDatabase.databaseDeleteExecutor.execute { dao.delete(plan) }
    }
}
