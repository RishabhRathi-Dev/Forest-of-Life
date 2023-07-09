package com.rishabh.forestoflife.navigation

interface Destinations{
    val route: String
}

object Onboarding: Destinations{
    override val route = "Onboarding"
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

object Tasks: Destinations{
    override val route = "Tasks"
}

object Create: Destinations{
    override val route = "Create"
}