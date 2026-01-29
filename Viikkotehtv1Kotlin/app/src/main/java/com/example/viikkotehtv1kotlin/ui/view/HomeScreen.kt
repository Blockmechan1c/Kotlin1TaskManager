package com.example.viikkotehtv1kotlin.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtv1kotlin.domain.viewmodel.TaskViewModel
import com.example.viikkotehtv1kotlin.domain.model.Task
import java.time.LocalDate

@Composable
fun HomeScreen(taskViewModel: TaskViewModel = viewModel()) {

    val tasks by taskViewModel.tasks.collectAsState()

    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    var newDueDate by remember { mutableStateOf(LocalDate.now()) }
    var showDetail by remember { mutableStateOf<Task?>(null) }

    Column(Modifier.padding(16.dp)) {

        // Title input
        OutlinedTextField(
            value = newTitle,
            onValueChange = { newTitle = it },
            label = { Text("New task title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Description input
        OutlinedTextField(
            value = newDescription,
            onValueChange = { newDescription = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Due date selector (simple +1 day button)
        Button(
            onClick = { newDueDate = newDueDate.plusDays(1) }
        ) {
            Text("Due date: $newDueDate")
        }

        Spacer(Modifier.height(8.dp))

        // Add task button
        Button(
            onClick = {
                if (newTitle.isNotBlank()) {
                    taskViewModel.addTask(
                        Task(
                            id = tasks.size + 1,
                            title = newTitle,
                            description = newDescription,
                            priority = 1,
                            dueDate = newDueDate,
                            done = false
                        )
                    )
                    newTitle = ""
                    newDescription = ""
                    newDueDate = LocalDate.now()
                }
            }
        ) {
            Text("Add Task")
        }

        Spacer(Modifier.height(16.dp))

        // Task list (sorted by due date in ViewModel)
        LazyColumn {
            items(tasks) { task ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { taskViewModel.toggleDone(task.id) }
                    )

                    Column(
                        Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(task.title)
                        Text(task.description, style = MaterialTheme.typography.bodySmall)
                        Text("Due: ${task.dueDate}", style = MaterialTheme.typography.bodySmall)
                    }

                    Button(onClick = { showDetail = task }) {
                        Text("Edit")
                    }
                }
            }
        }
    }

    // Detail dialog
    if (showDetail != null) {
        DetailScreen(
            task = showDetail!!,
            onSave = {
                taskViewModel.updateTask(it)
                showDetail = null
            },
            onDelete = {
                taskViewModel.removeTask(it.id)
                showDetail = null
            },
            onDismiss = { showDetail = null }
        )
    }
}
