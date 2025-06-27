package com.yourproblem.eatsmart

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.yourproblem.eatsmart.ui.generate.Diet
import com.yourproblem.eatsmart.ui.generate.Gender
import com.yourproblem.eatsmart.ui.generate.GenerateState
import com.yourproblem.eatsmart.ui.generate.GenerateViewModel
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoMoreInteractions


class GenerateViewModelValidationTest {

    @get:Rule val instant = InstantTaskExecutorRule()

    private val context = mock<Application> { }
    private val vm = GenerateViewModel(context)

    @Test
    fun `valid inputs transitions to loading`() {
        vm.gender.value = Gender.MALE
        vm.weight.value = "80"
        vm.diet.value = Diet.BALANCED

        vm.tryGenerate()

        assertTrue(vm.state.value is GenerateState.Loading)
    }
}
