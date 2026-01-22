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


