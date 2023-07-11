package com.rishabh.forestoflife.composables.utils.bottom

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



}