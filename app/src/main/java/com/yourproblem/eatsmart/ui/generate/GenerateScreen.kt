package com.yourproblem.eatsmart.ui.generate

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourproblem.eatsmart.data.model.MealPlan
import kotlinx.coroutines.delay

/* ─────────────────────────  MAIN  ───────────────────────── */

@Composable
fun GenerateScreen(
    onCompleted: (MealPlan) -> Unit,
    onCancel: () -> Unit,
    viewModel: GenerateViewModel = viewModel(
        factory = androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
            .getInstance(LocalContext.current.applicationContext as android.app.Application)
    )
) {
    val state by viewModel.state.observeAsState(GenerateState.Idle)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            GenerateState.Idle,
            is GenerateState.Error -> InputCard(viewModel, state as? GenerateState.Error, onCancel)

            GenerateState.Loading -> LoadingCard()

            is GenerateState.Success -> {
                val plan = (state as GenerateState.Success).plan
                LaunchedEffect(plan) { onCompleted(plan) }
            }
        }
    }
}

/* ────────────────────  INPUT  ──────────────────── */

@Composable
private fun InputCard(
    vm: GenerateViewModel,
    error: GenerateState.Error?,
    onCancel: () -> Unit
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Personalise Your Plan",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))

            /* Gender */
            val gender by vm.gender.observeAsState()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Gender.values().forEach { g ->
                    AssistChip(
                        onClick = { vm.gender.value = g },
                        label = { Text(g.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        leadingIcon = { if (gender == g) Icon(Icons.Filled.CheckCircle, null) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            /* Weight */
            val weight by vm.weight.observeAsState("")
            OutlinedTextField(
                value = weight,
                onValueChange = { vm.weight.value = it },
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            /* Diet */
            val diet by vm.diet.observeAsState()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Diet.values().forEach { d ->
                    AssistChip(
                        onClick = { vm.diet.value = d },
                        label = { Text(d.name.replace("_", "-").lowercase().replaceFirstChar { it.uppercase() }) },
                        leadingIcon = { if (diet == d) Icon(Icons.Filled.CheckCircle, null) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(onClick = { vm.tryGenerate() }, modifier = Modifier.fillMaxWidth()) {
                Text("Generate Plan")
            }
            Spacer(Modifier.height(12.dp))
            Button(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel")
            }

            error?.let {
                Spacer(Modifier.height(16.dp))
                Text(it.msg, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

/* ────────────────────  LOADING  ──────────────────── */

private val phrases = listOf(
    "Sharpening chef knives…",
    "Summoning grass-fed beef…",
    "Roasting buckwheat to perfection…",
    "Balancing macros like a ninja…",
    "Cracking free-range eggs…",
    "Stir-frying liver with attitude…"
)

@Composable
private fun LoadingCard() {
    ElevatedCard(
        modifier = Modifier.wrapContentSize()       // keeps card compact so centering is correct
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Plan is being generated…",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(16.dp))

            CircularProgressIndicator()
            Spacer(Modifier.height(20.dp))

            /* alpha pulse */
            val pulse by rememberInfiniteTransition().animateFloat(
                initialValue = 0.4f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(900, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            /* phrase rotation */
            var index by remember { mutableStateOf(0) }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(5_000)
                    index = (index + 1) % phrases.size
                }
            }

            Text(
                phrases[index],
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.graphicsLayer(alpha = pulse)
            )
        }
    }
}
