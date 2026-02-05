package com.example.viikkotehtv1kotlin.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.viikkotehtv1kotlin.domain.model.Task
import com.example.viikkotehtv1kotlin.domain.viewmodel.TaskViewModel
import com.example.viikkotehtv1kotlin.navigation.ROUTE_CALENDAR
import com.example.viikkotehtv1kotlin.navigation.ROUTE_SETTINGS
import java.time.LocalDate

enum class SortMode { ALL, DONE, NOT_DONE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    taskViewModel: TaskViewModel = viewModel()
) {
    val tasks by taskViewModel.tasks.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var editTask by remember { mutableStateOf<Task?>(null) }
    var sortMode by remember { mutableStateOf(SortMode.ALL) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                actions = {

                    // SORT BUTTON
                    IconButton(
                        onClick = {
                            sortMode = when (sortMode) {
                                SortMode.ALL -> SortMode.NOT_DONE
                                SortMode.NOT_DONE -> SortMode.DONE
                                SortMode.DONE -> SortMode.ALL
                            }
                        }
                    ) {
                        val label = when (sortMode) {
                            SortMode.ALL -> "All"
                            SortMode.NOT_DONE -> "Not done"
                            SortMode.DONE -> "Done"
                        }
                        Text(label)
                    }

                    // ADD TASK
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Task")
                    }

                    // CALENDAR
                    IconButton(onClick = { navController.navigate(ROUTE_CALENDAR) }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Calendar")
                    }

                    // SETTINGS
                    IconButton(onClick = { navController.navigate(ROUTE_SETTINGS) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->

        // APPLY SORTING
        val filteredTasks = when (sortMode) {
            SortMode.ALL -> tasks
            SortMode.DONE -> tasks.filter { it.done }
            SortMode.NOT_DONE -> tasks.filter { !it.done }
        }

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(filteredTasks) { task ->
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

                    Button(onClick = { editTask = task }) {
                        Text("Edit")
                    }
                }
            }
        }
    }

    // ADD TASK DIALOG
    if (showAddDialog) {
        AddTaskDialog(
            onSave = { title, desc, date ->
                taskViewModel.addTask(
                    Task(
                        id = tasks.size + 1,
                        title = title,
                        description = desc,
                        priority = 1,
                        dueDate = date,
                        done = false
                    )
                )
                showAddDialog = false
            },
            onCancel = { showAddDialog = false }
        )
    }

    // EDIT TASK DIALOG
    if (editTask != null) {
        EditTaskDialog(
            task = editTask!!,
            onSave = { updated ->
                taskViewModel.updateTask(updated)
                editTask = null
            },
            onDelete = {
                taskViewModel.removeTask(it.id)
                editTask = null
            },
            onCancel = { editTask = null }
        )
    }
}

@Composable
fun AddTaskDialog(
    onSave: (String, String, LocalDate) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Add Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") }
                )
                Button(onClick = { date = date.plusDays(1) }) {
                    Text("Due: $date")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { if (title.isNotBlank()) onSave(title, desc, date) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EditTaskDialog(
    task: Task,
    onSave: (Task) -> Unit,
    onDelete: (Task) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var desc by remember { mutableStateOf(task.description) }
    var date by remember { mutableStateOf(task.dueDate) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Edit Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") }
                )
                Button(onClick = { date = date.plusDays(1) }) {
                    Text("Due: $date")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(task.copy(title = title, description = desc, dueDate = date))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = onCancel) { Text("Cancel") }
                TextButton(onClick = { onDelete(task) }) { Text("Delete") }
            }
        }
    )
}
