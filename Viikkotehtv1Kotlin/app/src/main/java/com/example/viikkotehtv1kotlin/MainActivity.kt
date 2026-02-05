package com.example.viikkotehtv1kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikkotehtv1kotlin.navigation.ROUTE_HOME
import com.example.viikkotehtv1kotlin.navigation.ROUTE_CALENDAR
import com.example.viikkotehtv1kotlin.navigation.ROUTE_SETTINGS
import com.example.viikkotehtv1kotlin.ui.theme.Viikkotehtävä1KotlinTheme
import com.example.viikkotehtv1kotlin.ui.view.CalendarScreen
import com.example.viikkotehtv1kotlin.ui.view.HomeScreen
import com.example.viikkotehtv1kotlin.ui.view.SettingsScreen
import com.example.viikkotehtv1kotlin.domain.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Viikkotehtävä1KotlinTheme {

                val navController = rememberNavController()
                val taskViewModel: TaskViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_HOME
                ) {
                    composable(ROUTE_HOME) {
                        HomeScreen(navController, taskViewModel)
                    }
                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(navController, taskViewModel)
                    }
                    composable(ROUTE_SETTINGS) {
                        SettingsScreen(navController)
                    }
                }
            }
        }
    }
}
