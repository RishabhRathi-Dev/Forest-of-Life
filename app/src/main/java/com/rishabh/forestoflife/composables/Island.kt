package com.rishabh.forestoflife.composables

import android.view.SurfaceView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader

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
            ) {
                IslandScreen()
            }
        }
    }
}

@Composable
fun IslandScreen(){
    val context = LocalContext.current
    val surfaceView = remember { SurfaceView(context) }
    val customViewer = remember { CustomViewer() }

    // Handle initialization and cleanup with DisposableEffect
    DisposableEffect(surfaceView) {
        customViewer.init(surfaceView.context, surfaceView)
        customViewer.createRenderables("mouse", "mouse 2")
        customViewer.createIndirectLight("pillars_2k")
        customViewer.onResume()

        onDispose {
            customViewer.onPause()
            customViewer.onDestroy()
        }
    }

    // Use ViewCompositionStrategy to control the view's lifecycle
    AndroidView(
        factory = { surfaceView },
        modifier = Modifier.fillMaxSize(), // Adjust the modifier as needed
        update = {}
    )
}

@Preview
@Composable
fun IslandPreview(){

}