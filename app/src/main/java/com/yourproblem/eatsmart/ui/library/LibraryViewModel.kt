package com.yourproblem.eatsmart.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.yourproblem.eatsmart.data.local.MealPlanDatabase
import com.yourproblem.eatsmart.data.model.MealPlan
import com.yourproblem.eatsmart.data.repository.MealPlanRepository

class LibraryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MealPlanDatabase.getDatabase(application)

    private val repository = MealPlanRepository(database.mealPlanDao())

    val plans = repository.plans
    fun deletePlan(plan: MealPlan) = repository.delete(plan)
}
