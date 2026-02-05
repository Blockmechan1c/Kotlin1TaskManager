package com.example.viikkotehtv1kotlin.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.viikkotehtv1kotlin.domain.viewmodel.TaskViewModel
import com.example.viikkotehtv1kotlin.navigation.ROUTE_HOME
import com.example.viikkotehtv1kotlin.navigation.ROUTE_SETTINGS
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    taskViewModel: TaskViewModel
) {
    val tasks by taskViewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUTE_HOME) }) {
                        Icon(Icons.Default.List, contentDescription = "Home")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(ROUTE_SETTINGS) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->

        val grouped = tasks.groupBy { it.dueDate }

        LazyColumn(Modifier.padding(padding).padding(16.dp)) {

            grouped.forEach { (date, tasksForDate) ->

                item {
                    Text(
                        text = date.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(tasksForDate) { task ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { taskViewModel.toggleDone(task.id) }
                        )

                        Column(Modifier.weight(1f).padding(start = 8.dp)) {
                            Text(task.title)
                            Text(task.description, style = MaterialTheme.typography.bodySmall)
                        }

                        Button(onClick = { /* optional: open edit dialog */ }) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }
}
