# Kotlin1TaskManager


WEEK1
Task Manager App (Jetpack Compose)
This is a small Android app built with Jetpack Compose.
The app displays a list of tasks and allows the user to:

- Add a new task

- Mark tasks as done/undo

- Filter tasks by done state

- Sort tasks by due date

- Reset back to mock data

Data Model
The app uses a Task data class:

kotlin
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val dueDate: LocalDate,
    val done: Boolean
)
Mock tasks 8items are stored in the domain package.

Kotlin Functions
All task operations are pure functions:

addTask(list, task)  
Adds a new task to the end of the list.

toggleDone(list, id)  
Flips the done value of the task with the given ID.

filterByDone(list, done)  
Returns only tasks that match the given done state.

sortByDueDate(list)  
Sorts tasks by their due date.

UI (HomeScreen)
The UI is built with Jetpack Compose using:

- Column, Row, Spacer, Modifier

- OutlinedTextField for task input

- Buttons for actions
  
- A simple list showing:

- Title

- Due date

- Done/Undo button

The task list updates automatically using remember { mutableStateOf(...) }.


WEEK2
Compose State Management
Jetpack Compose updates the UI automatically whenever the underlying state changes.
mutableStateOf makes a variable reactive, meaning Compose will recompose the UI when its value changes.
remember stores that state only as long as the Composable is in memory — if the Composable is destroyed and recreated, the value resets.

Why ViewModel is better than just remember
Survives configuration changes – remember resets when the screen rotates, but a ViewModel keeps its state.

Persists across navigation – a Composable can be destroyed and recreated, but the ViewModel stays alive.

Separates logic from UI – data handling and business logic stay in the ViewModel instead of cluttering the UI layer.

Officially recommended by Android – ViewModel is the standard way to manage long‑lived UI state in Android apps.


WEEK3
What is MVVM and why is it useful in Jetpack Compose
MVVM (Model–View–ViewModel) is an architecture pattern that separates your app into three parts:

Model – holds the data and business logic

View – the UI layer (Jetpack Compose)

ViewModel – stores UI state and exposes functions for the UI

Jetpack Compose is reactive, meaning the UI automatically updates when the underlying state changes.
MVVM fits this perfectly because the ViewModel owns the state, and the View simply observes it.
This keeps the UI clean, testable, and easy to maintain.

How StateFlow works
StateFlow is a state holder that emits updates to anyone observing it.

Key points:

It always contains one current value

When the value changes, all collectors receive the update

Compose can observe it using collectAsState()

When the ViewModel updates the value, the UI recomposes automatically

Example:

kotlin
val tasks by viewModel.tasks.collectAsState()
Whenever the ViewModel does:

kotlin
_tasks.value = newList
the UI updates instantly without any manual refresh logic.
