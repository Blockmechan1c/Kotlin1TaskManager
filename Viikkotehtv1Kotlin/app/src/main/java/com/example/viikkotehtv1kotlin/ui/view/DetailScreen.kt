package com.example.viikkotehtv1kotlin.ui.view

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.viikkotehtv1kotlin.domain.model.Task

@Composable
fun DetailScreen(
    task: Task,
    onSave: (Task) -> Unit,
    onDelete: (Task) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf(task.title) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(task.copy(title = title))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDelete(task) }) {
                Text("Delete")
            }
        }
    )
}
