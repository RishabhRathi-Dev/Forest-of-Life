package com.rishabh.forestoflife.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import com.rishabh.forestoflife.data.AppViewModel

@Composable
fun Focus(navHostController : NavHostController){
    // TODO:: Create Focus Page
    Scaffold(
        topBar = { MainHeader(pageName = "Focus") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {
        Modifier.padding(it)
    }

}

@Preview
@Composable
fun FocusPreview(){

}