package com.rishabh.forestoflife.composables.utils.bottom

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {

    /* for home
    object Home: BottomBarScreen(
        route = "Home",
        title = "Home",
        icon = R.drawable.ic_bottom_home,
        icon_focused = R.drawable.ic_bottom_home_focused
    )

     */
}