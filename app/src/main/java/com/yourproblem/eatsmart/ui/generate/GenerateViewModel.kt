package com.yourproblem.eatsmart.ui.generate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yourproblem.eatsmart.data.local.MealPlanDatabase
import com.yourproblem.eatsmart.data.model.MealPlan
import com.yourproblem.eatsmart.data.model.MealPlanRequest
import com.yourproblem.eatsmart.data.repository.MealPlanRepository
import com.yourproblem.eatsmart.network.MealPlanApiProvider
import com.yourproblem.eatsmart.network.MealPlanResult
import java.util.concurrent.Executors

/* ────────── USER-INPUT ENUMS ────────── */
enum class Gender { MALE, FEMALE }
enum class Diet   { BALANCED, KETO, LOW_CARB }

/* ────────── VIEWMODEL ────────── */
class GenerateViewModel(application: Application) : AndroidViewModel(application), MealPlanResult {

    private val repository =
        MealPlanRepository(MealPlanDatabase.getDatabase(application).mealPlanDao())
    private val apiProvider = MealPlanApiProvider()
    private val io = Executors.newSingleThreadExecutor()

    /* user inputs */
    val gender = MutableLiveData<Gender?>(null)
    val weight = MutableLiveData<String>("")
    val diet   = MutableLiveData<Diet?>(null)

    /* ui state */
    private val _state = MutableLiveData<GenerateState>(GenerateState.Idle)
    val state: LiveData<GenerateState> = _state

    /* public entry */
    fun tryGenerate() {
        if (_state.value is GenerateState.Loading) return

        when (val error = validateInputs()) {
            null -> {
                val request = MealPlanRequest(
                    gender.value!!.name,
                    weight.value!!.toFloat(),
                    diet.value!!.name
                )
                startGeneration(request)
            }
            else  -> _state.value = GenerateState.Error(error)
        }
    }

    /* MealPlanResult callbacks */
    override fun onSuccessGeneratePlan(plan: MealPlan) {
        io.execute { repository.insert(plan) }
        _state.postValue(GenerateState.Success(plan))
    }

    override fun onFailGeneratePlan() {
        _state.postValue(GenerateState.Error("Failed to generate plan"))
    }

    /* helpers */
    private fun startGeneration(request: MealPlanRequest) {
        _state.value = GenerateState.Loading
        apiProvider.generatePlan(this, request)
    }

    private fun validateInputs(): String? =
        when {
            gender.value == null                     -> "Select your gender"
            diet.value == null                       -> "Select a diet style"
            weight.value?.trim().isNullOrEmpty()     -> "Enter your weight"
            weight.value!!.toFloatOrNull()?.let { it <= 0 } ?: true -> "Weight must be positive"
            else                                     -> null
        }
}
