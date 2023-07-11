package com.rishabh.forestoflife.composables

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rishabh.forestoflife.navigation.Focus
import com.rishabh.forestoflife.navigation.Home
import com.rishabh.forestoflife.navigation.Island
import com.rishabh.forestoflife.navigation.Onboarding1
import com.rishabh.forestoflife.navigation.Onboarding2
import com.rishabh.forestoflife.navigation.Profile

@Composable
fun NavigationComposable(context: Context, navController: NavHostController) {

    val sharedPreferences = context.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
    var startDestination = Onboarding1.route

    if (sharedPreferences.getBoolean("userRegistered", false)) {
        startDestination = Home.route
    }

    NavHost(navController = navController, startDestination = startDestination){
        // Onboarding
        composable(Onboarding1.route){
            Onboarding1(navController)
        }

        composable(Onboarding2.route){
            Onboarding2(navController)
        }

        // Main
        composable(Home.route){
            //Home(navController)
            Home()
        }

        composable(Focus.route){
            Focus()
        }

        composable(Island.route){
            Island()
        }

        composable(Profile.route){
            Profile()
        }

        //Others
    }
}