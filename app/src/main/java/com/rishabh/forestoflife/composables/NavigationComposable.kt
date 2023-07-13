package com.rishabh.forestoflife.composables

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rishabh.forestoflife.composables.extras.ChoosePlant
import com.rishabh.forestoflife.composables.extras.ChooseTree
import com.rishabh.forestoflife.composables.extras.CreateTask
import com.rishabh.forestoflife.composables.extras.TaskList
import com.rishabh.forestoflife.navigation.ChoosePlant
import com.rishabh.forestoflife.navigation.ChooseTree
import com.rishabh.forestoflife.navigation.CreateTask
import com.rishabh.forestoflife.navigation.Focus
import com.rishabh.forestoflife.navigation.Home
import com.rishabh.forestoflife.navigation.Island
import com.rishabh.forestoflife.navigation.Onboarding1
import com.rishabh.forestoflife.navigation.Onboarding2
import com.rishabh.forestoflife.navigation.Profile
import com.rishabh.forestoflife.navigation.TaskList

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
            Home(navController)
        }

        composable(Focus.route){
            Focus(navController)
        }

        composable(Island.route){
            Island(navController)
        }

        composable(Profile.route){
            Profile(navController)
        }

        //Others
        composable(TaskList.route){
            TaskList(navController)
        }

        composable(CreateTask.route){
            CreateTask()
        }

        composable(ChoosePlant.route){
            ChoosePlant()
        }

        composable(ChooseTree.route){
            ChooseTree()
        }
    }
}