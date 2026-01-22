package com.example.viikkotehtv1kotlin.domain

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class TaskViewModel : ViewModel() {

    var tasks by mutableStateOf(listOf<Task>())
        private set

    init {
        tasks = mockTasks
    }

    fun addTask(task: Task){
        tasks = tasks + task
    }

    fun toggleDone(id: Int){
        tasks = tasks.map{
            if (it.id == id) it.copy(done = !it.done) else it
        }
    }

    fun removeTask(id: Int) {
        tasks = tasks.filterNot { it.id == id }
    }

    fun filterByDone(done: Boolean) {
        tasks = mockTasks.filter { it.done == done }
    }

    fun sortByDueDate(){
        tasks = tasks.sortedBy { it.dueDate }
    }
}