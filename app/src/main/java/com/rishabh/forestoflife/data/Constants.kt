package com.rishabh.forestoflife.data

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

const val MAX_POINTS = 350
const val MAX_TIME = 45*60*1000
const val DEDUCTION = 50
val REQUESTCODE = Random.nextInt(100000, 999999)
val GRAPHICS_SETTINGS =
    arrayOf("Low", "Medium", "High", "Ultra")

val colorsForHours = listOf(
    Color(0xFF001832), // (12:00 AM)
    Color(0xFF001d37), // (1:00 AM)
    Color(0xFF001e39), // (2:00 AM)
    Color(0xFF00243F), // (3:00 AM)
    Color(0xFF012847), // (4:00 AM)
    Color(0xFF00304a), // (5:00 AM)
    Color(0xFF054162), // (6:00 AM)
    Color(0xFF1386AA), // (7:00 AM)
    Color(0xFF309db1), // (8:00 AM)
    Color(0xFF8cd9c9), // (9:00 AM)
    Color(0xFF8cf1d0), // (10:00 AM)
    Color(0xFF8cf3d1), // (11:00 AM)
    Color(0xFF8cf5d2), // (12:00 PM / Noon)
    Color(0xFF8cf7d3), // (1:00 PM)
    Color(0xFF8cf9d4), // (2:00 PM)
    Color(0xFF8cffd5), // (3:00 PM)
    Color(0xFF8cd9d6), // (4:00 PM)
    Color(0xFF309db1), // (5:00 PM)
    Color(0xFF054162), // (6:00 PM)
    Color(0xFF012847), // (7:00 PM)
    Color(0xFF00243F), // (8:00 PM)
    Color(0xFF001e39), // (9:00 PM)
    Color(0xFF001d37), // (10:00 PM)
    Color(0xFF001832)  // (11:00 PM)
)

