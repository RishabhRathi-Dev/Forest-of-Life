package com.rishabh.forestoflife.navigation

interface Destinations{
    val route: String
}

object Onboarding1: Destinations{
    override val route = "Onboarding1"
}

object Onboarding2: Destinations{
    override val route = "Onboarding2"
}

object Home: Destinations{
    override val route = "Home"
}

object Focus: Destinations{
    override val route = "Focus"
}

object Island: Destinations{
    override val route = "Island"
}

object Profile: Destinations{
    override val route = "Profile"
}

// Others

object TaskList: Destinations{
    override val route = "TaskList"
}

object CreateTask: Destinations{
    override val route = "CreateTask"
}

object Help : Destinations{
    override val route = "Help"
}
