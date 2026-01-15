package com.example.viikkotehtv1kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.viikkotehtv1kotlin.domain.Task
import com.example.viikkotehtv1kotlin.domain.addTask
import com.example.viikkotehtv1kotlin.domain.toggleDone
import com.example.viikkotehtv1kotlin.domain.filterByDone
import com.example.viikkotehtv1kotlin.domain.sortByDueDate

import com.example.viikkotehtv1kotlin.ui.theme.Viikkotehtävä1KotlinTheme
import com.example.viikkotehtv1kotlin.domain.mockTasks
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Viikkotehtävä1KotlinTheme {
                HomeScreen()
            }
        }
    }
}


@Composable
fun HomeScreen() {

    var tasks by remember { mutableStateOf(mockTasks) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Task Manager",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )

        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = priority,
            onValueChange = { priority = it },
            label = { Text("Priority (number)") }
        )

        Spacer(Modifier.height(16.dp))

        Row {
            Button(onClick = {
                if (title.isNotBlank() && priority.isNotBlank()) {
                    val newTask = Task(
                        id = tasks.size + 1,
                        title = title,
                        description = description,
                        priority = priority.toIntOrNull() ?: 1,
                        dueDate = LocalDate.now(),
                        done = false
                    )
                    tasks = addTask(tasks, newTask)

                    title = ""
                    description = ""
                    priority = ""
                }
            }) {
                Text("Add Task")
            }

            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                tasks = sortByDueDate(tasks)
            }) {
                Text("Sort by Date")
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = {
                tasks = filterByDone(tasks, true)
            }) {
                Text("Show Done")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                tasks = filterByDone(tasks, false)
            }) {
                Text("Show Due")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                tasks = mockTasks
            }) {
                Text("Reset")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        tasks.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = task.title,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Due: ${task.dueDate}",
                    modifier = Modifier.weight(1f)
                )

                Button(onClick = {
                    tasks = toggleDone(tasks, task.id)
                }) {
                    Text(if (task.done) "Undo" else "Done")
                }
            }
        }
    }
}
