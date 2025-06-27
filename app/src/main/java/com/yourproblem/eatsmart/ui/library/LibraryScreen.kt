package com.yourproblem.eatsmart.ui.library

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yourproblem.eatsmart.data.model.MealPlan
import com.yourproblem.eatsmart.ui.login.LoginActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onPlanSelected: (MealPlan) -> Unit,
    onGenerate: () -> Unit,
    viewModel: LibraryViewModel,
    onSignOut: () -> Unit
) {
    val plans by viewModel.plans.observeAsState(emptyList())


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Eat Smart", textAlign = TextAlign.Center) },
                navigationIcon = { Icon(Icons.Default.Home, contentDescription = null) },
                actions = {
                    IconButton(onClick = onSignOut) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sign Out"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Row (
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { throw RuntimeException("Test Crash") }) {
                    Text(text = "Crash")
                }

            FloatingActionButton(onClick = onGenerate) {
                Icon(Icons.Default.Add, null)
            }

            }
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            items(plans) { plan ->
                MealPlanRow(
                    plan = plan,
                    onPlanSelected = onPlanSelected,
                    onDelete = { viewModel.deletePlan(it) }
                )
            }

        }
    }
}

@Composable
private fun MealPlanRow(
    plan: MealPlan,
    onPlanSelected: (MealPlan) -> Unit,
    onDelete: (MealPlan) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onDelete(plan)
                    }
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            },
            title = { Text("Delete plan?") },
            text = { Text("This will permanently remove the meal plan.") }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlanSelected(plan) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(plan.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("${plan.startDate} — ${plan.endDate}", style = MaterialTheme.typography.bodySmall)
        }
        IconButton(onClick = { showDialog = true }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
