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
Compose‑tilanhallinta 

Jetpack Compose päivittää käyttöliittymän automaattisesti aina, kun tila muuttuu.
mutableStateOf tekee muuttujasta reaktiivisen, ja remember säilyttää sen vain niin kauan kuin Composable on elossa.

Miksi ViewModel on parempi kuin pelkkä remember?
Säilyy ruudun kääntyessä – remember nollaantuu, ViewModel ei.

Ei katoa navigoidessa – Composable voi tuhoutua, mutta ViewModel pysyy.

Erottaa logiikan UI:sta – listan käsittely pysyy siististi ViewModelissa.

On Androidin suosittelema tapa hallita sovelluksen pysyvää tilaa.
