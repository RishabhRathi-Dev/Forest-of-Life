package com.rishabh.forestoflife.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.filament.IndirectLight
import com.google.android.filament.Skybox
import com.gorisse.thomas.lifecycle.lifecycle
import com.gorisse.thomas.lifecycle.lifecycleScope
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import io.github.sceneview.Scene
import io.github.sceneview.loaders.loadHdrIndirectLight
import io.github.sceneview.loaders.loadHdrIndirectLightAsync
import io.github.sceneview.loaders.loadHdrSkybox
import io.github.sceneview.loaders.loadHdrSkyboxAsync
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.model.Model
import io.github.sceneview.nodes.ModelNode
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
            ) {
                IslandScreen()
            }
        }
    }
}

@Composable
fun IslandScreen(){
    val nodes = remember { mutableStateListOf<Node>() }
    var loadStatus = remember {
        mutableStateOf(false)
    }

    var hdrStatus = remember {
        mutableStateOf(false)
    }

    var skyStatus = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scene(
            modifier = Modifier.fillMaxSize(),
            nodes = nodes,
            onCreate = { sceneView ->
                // Apply your configuration
                sceneView.apply {
                    setLifecycle(lifecycle)
                    // Apply your configuration
                    sceneView.lifecycleScope.launchWhenCreated {
                        val hdrFile = "environments/kloofendal_48d_partly_cloudy_puresky_4k.hdr"
                        sceneView.loadHdrIndirectLight(hdrFile, specularFilter = true) {
                            intensity(30_000f)
                        }
                        sceneView.loadHdrSkybox(hdrFile) {
                            intensity(50_000f)
                        }

                        val model = sceneView.modelLoader.loadModel("models/mouse 2.glb")!!
                        val modelNode = ModelNode(sceneView, model).apply {
                            transform(
                                position = Position(z = -4.0f),
                                rotation = Rotation(x = 15.0f)
                            )
                            scaleToUnitsCube(2.0f)
                        }
                        sceneView.addChildNode(modelNode)

                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun IslandPreview(){

}