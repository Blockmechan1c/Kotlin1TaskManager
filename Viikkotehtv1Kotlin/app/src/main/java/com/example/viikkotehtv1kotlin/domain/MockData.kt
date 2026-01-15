package com.example.viikkotehtv1kotlin.domain

import java.time.LocalDate;

val mockTasks = listOf(
    Task(1,"juo vettä", "ota lasi ja laita vettä siihen ja juo", 1, LocalDate.now(), true),
    Task(2, "syö", "ota ruokaa ja syö", 2, LocalDate.now(), false),
    Task(3, "Katso One Piece", "Avaa crunchyroll ja katso One Piece", 3, LocalDate.now(), true),
    Task(4, "nuku", "mene nukkumaan", 4, LocalDate.now(), true),
    Task(5, "ui", "ui", 5, LocalDate.now(), false),
    Task(6, "soita", "soita musiikkia", 6, LocalDate.now(), true),
    Task(7, "sauno", "mene saunaan", 7, LocalDate.now(), true),
    Task(8, "kahvi", "juo", 8, LocalDate.now(), false)
)