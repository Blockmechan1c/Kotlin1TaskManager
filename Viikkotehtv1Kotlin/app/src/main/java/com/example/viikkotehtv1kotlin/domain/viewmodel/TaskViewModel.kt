package com.example.viikkotehtv1kotlin.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viikkotehtv1kotlin.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // Sort helper
    private fun sortTasks(list: List<Task>): List<Task> {
        return list.sortedBy { it.dueDate }
    }

    // Add a new task
    fun addTask(task: Task) {
        _tasks.value = sortTasks(_tasks.value + task)
    }

    // Toggle done state
    fun toggleDone(id: Int) {
        _tasks.value = sortTasks(
            _tasks.value.map { task ->
                if (task.id == id) task.copy(done = !task.done)
                else task
            }
        )
    }

    // Update an existing task
    fun updateTask(updated: Task) {
        _tasks.value = sortTasks(
            _tasks.value.map { task ->
                if (task.id == updated.id) updated
                else task
            }
        )
    }

    // Remove a task
    fun removeTask(id: Int) {
        _tasks.value = sortTasks(
            _tasks.value.filterNot { it.id == id }
        )
    }
}
