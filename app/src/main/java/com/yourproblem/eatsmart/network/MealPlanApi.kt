package com.yourproblem.eatsmart.network

import com.yourproblem.eatsmart.data.model.MealPlan
import com.yourproblem.eatsmart.data.model.MealPlanRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MealPlanApi {
    @POST("generate/")
    fun generatePlan(@Body request: MealPlanRequest): Call<MealPlan>
}