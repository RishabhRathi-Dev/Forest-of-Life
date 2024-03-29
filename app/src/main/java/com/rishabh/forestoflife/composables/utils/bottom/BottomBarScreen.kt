package com.rishabh.forestoflife.composables.utils.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.rishabh.forestoflife.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {
    object Home: BottomBarScreen(
        route = "Home",
        title = "Home",
        icon = R.drawable.home_48px,
        icon_focused = R.drawable.home_focus
    )

    object Focus: BottomBarScreen(
        route = "Focus",
        title = "Focus",
        icon = R.drawable.mindfulness_48px,
        icon_focused = R.drawable.mindfulness_focus
    )

    object Add: BottomBarScreen(
        route = "CreateTask",
        title = "CreateTask",
        icon = 0,
        icon_focused = 0
    )

    object Island: BottomBarScreen(
        route = "Island",
        title = "Island",
        icon = R.drawable.nature_48px,
        icon_focused = R.drawable.nature_focus
    )

    object Profile: BottomBarScreen(
        route = "Profile",
        title = "Profile",
        icon = R.drawable.person_48px,
        icon_focused = R.drawable.person_focus
    )



}