package com.rishabh.forestoflife.composables.extras

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.headers.AlternateHeader

@Composable
fun Help(navHostController:NavHostController){
    Scaffold(
        topBar = { AlternateHeader(pageName = "Help", navHostController = navHostController) },
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ){

        }
    }
}

@Composable
fun HomeHelp(){

}

@Composable()
fun FocusHelp(){

}

@Composable
fun IslandHelp(){

}

@Composable
fun RewardsHelp(){

}

@Composable
fun BugsIssuesHelp(){

}