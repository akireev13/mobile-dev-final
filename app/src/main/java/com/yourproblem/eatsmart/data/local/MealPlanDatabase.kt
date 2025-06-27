package com.yourproblem.eatsmart.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yourproblem.eatsmart.data.model.MealPlan
import com.yourproblem.eatsmart.data.model.PlanConverters
import java.util.concurrent.Executors

@Database(entities = [MealPlan::class], version = 2, exportSchema = false)
@TypeConverters(PlanConverters::class)
abstract class MealPlanDatabase : RoomDatabase() {
    abstract fun mealPlanDao(): MealPlanDao

    companion object {
        @Volatile
        private var INSTANCE: MealPlanDatabase? = null

        val databaseWriteExecutor = Executors.newFixedThreadPool(2)
        val databaseDeleteExecutor = Executors.newFixedThreadPool(2)
        fun getDatabase(context: Context): MealPlanDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(context, MealPlanDatabase::class.java, "db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}
