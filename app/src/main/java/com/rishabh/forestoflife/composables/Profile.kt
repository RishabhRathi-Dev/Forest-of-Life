package com.rishabh.forestoflife.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader

@Composable
fun Profile(navHostController : NavHostController){
    //TODO:: Create Profile Page
    Scaffold(
        topBar = { MainHeader(pageName = "Profile") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {
        Modifier.padding(it)
    }
}

@Preview
@Composable
fun ProfilePreview(){

}