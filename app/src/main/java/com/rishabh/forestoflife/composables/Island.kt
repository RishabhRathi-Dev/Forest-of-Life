package com.rishabh.forestoflife.composables

import android.content.Context
import android.util.Log
import android.view.SurfaceView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import com.rishabh.forestoflife.data.AppViewModel

@Composable
fun Island(navHostController : NavHostController){
    // TODO: Create Island Page
    Scaffold(
        topBar = { MainHeader(pageName = "Island", navHostController = navHostController) },
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
                    .padding(horizontal = 16.dp)
                    .height((LocalConfiguration.current.screenHeightDp / 1.5).dp)
                    .clip(RoundedCornerShape(10.dp))
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

    val appViewModel : AppViewModel = viewModel()

    val point by appViewModel.getPoints().observeAsState()
    val time by appViewModel.getTime().observeAsState()

    val sharedPreferences = context.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
    val graphic = sharedPreferences.getInt("Graphics", 2)

    Log.d("Graphics", graphic.toString())

    var modelName = when (point) {
        in 0..50 -> {
            "00"
        }
        in 51..150 -> {
            "01"
        }
        in 151..250 ->{
            "12"
        }
        in 251..350 ->{
            "22"
        }
        else -> {
            "00"
        }
    }

    if (time != null){
        // 60 min of focus time and more than 250 points then special model
        modelName = when (time) {
            in 0L..15*60*1000 -> {
                modelName + "0"
            }
            in (15*60*1000L+1)..30*60*1000L -> {
                modelName + "1"
            }
            in 30*60*1000L+1..45*60*1000L - 1000 ->{
                modelName + "2"
            }
            else -> {
                modelName + "3"
            }
        }
    }

    /*
     DisposableEffect is used to handle the initialization and cleanup of the CustomViewer,
     ensuring that it starts and stops correctly with the Composable function.
     */

    // Handle initialization and cleanup with DisposableEffect
    if (point != null && time != null){
        DisposableEffect(surfaceView, modelName) {
            Log.d("Point", point.toString())
            Log.d("Time", time.toString())
            customViewer.init(surfaceView.context, surfaceView)
            when (graphic){
                0 -> {
                    customViewer.lowSetting()
                }

                1 -> {
                    customViewer.mediumSetting()
                }

                2 -> {
                    customViewer.highSetting()
                }

                3 -> {
                    customViewer.ultraSetting()
                }

                else -> {
                    customViewer.mediumSetting()
                }
            }
            customViewer.createRenderables("island", modelName)
            customViewer.createIndirectLight("pillars_2k")
            customViewer.onResume()

            onDispose {
                customViewer.onPause()
                customViewer.onDestroy()
            }
        }
    }
    // Use ViewCompositionStrategy to control the view's lifecycle
    AndroidView(
        factory = { surfaceView },
        modifier = Modifier.fillMaxSize(),
        update = {}
    )
}

@Preview
@Composable
fun IslandPreview(){

}