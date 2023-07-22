package com.rishabh.forestoflife.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import io.github.sceneview.Scene
import io.github.sceneview.loaders.loadHdrIndirectLight
import io.github.sceneview.nodes.Node

@Composable
fun Island(navHostController : NavHostController){
    // TODO: Create Island Page
    Scaffold(
        topBar = { MainHeader(pageName = "Island") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp / 1.5).dp)
                    .background(color = Color.Cyan)
            ) {

            }
        }
    }
}

@Composable
fun IslandScreen(){
    val nodes = remember { mutableStateListOf<Node>() }

    Box(modifier = Modifier.fillMaxSize()) {
        Scene(
            modifier = Modifier.fillMaxSize(),
            nodes = nodes,
            onCreate = { sceneView ->
                // Apply your configuration
            }
        )
    }
}

@Preview
@Composable
fun IslandPreview(){

}