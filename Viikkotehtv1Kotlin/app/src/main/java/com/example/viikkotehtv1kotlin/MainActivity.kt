package com.example.viikkotehtv1kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.viikkotehtv1kotlin.ui.theme.Viikkotehtävä1KotlinTheme
import com.example.viikkotehtv1kotlin.ui.view.HomeScreen

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


