package com.yourproblem.eatsmart.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yourproblem.eatsmart.data.model.MealPlan
import com.yourproblem.eatsmart.data.model.MealPlanRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://10.0.2.2:8000/api/"

private val retrofit by lazy {

    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY   // or BASIC
    }

    val client = OkHttpClient.Builder().addInterceptor(interceptor)
        .connectTimeout(180, TimeUnit.SECONDS) // Set connect timeout to 30 seconds
        .readTimeout(180, TimeUnit.SECONDS)    // Set read timeout to 30 seconds
        .writeTimeout(180, TimeUnit.SECONDS)
        .build()

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()
        .create<MealPlanApi>()
}

open class MealPlanApiProvider() {
    open fun generatePlan(cb: MealPlanResult, request: MealPlanRequest) {
        retrofit.generatePlan(request).enqueue(object: Callback<MealPlan> {
            override fun onResponse(call: Call<MealPlan>, response: Response<MealPlan>) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onSuccessGeneratePlan(response.body()!!)
                } else {
                    cb.onFailGeneratePlan()
                }
            }

            override fun onFailure(call: Call<MealPlan?>, t: Throwable) {
                cb.onFailGeneratePlan()
            }
        })
    }
}