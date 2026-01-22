package com.example.viikkotehtv1kotlin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtv1kotlin.domain.Task
import com.example.viikkotehtv1kotlin.domain.TaskViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(taskViewModel: TaskViewModel = viewModel()) {

    var title by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Task Manager", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        // --- Add new task ---
        Row {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("New task title") },
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            Button(onClick = {
                if (title.isNotBlank()) {
                    val newTask = Task(
                        id = taskViewModel.tasks.size + 1,
                        title = title,
                        description = "",
                        priority = 1,
                        dueDate = LocalDate.now(),
                        done = false
                    )
                    taskViewModel.addTask(newTask)
                    title = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(16.dp))

        // --- Buttons ---
        Row {
            Button(onClick = { taskViewModel.sortByDueDate() }) {
                Text("Sort by date")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { taskViewModel.filterByDone(true) }) {
                Text("Show done")
            }
        }

        Spacer(Modifier.height(16.dp))

        // --- Task list ---
        LazyColumn {
            items(taskViewModel.tasks) { task ->
                TaskRow(
                    task = task,
                    onToggle = { taskViewModel.toggleDone(task.id) },
                    onDelete = { taskViewModel.removeTask(task.id) }
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggle() }
            )
            Text(task.title, modifier = Modifier.padding(start = 8.dp))
        }

        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}
