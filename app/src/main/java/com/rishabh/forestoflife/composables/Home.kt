package com.rishabh.forestoflife.composables

import android.view.SurfaceView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.cards.TaskCard
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import com.rishabh.forestoflife.data.AppViewModel


@Composable
fun Home(navHostController : NavHostController){
    // TODO: Create Home

    val viewModel : AppViewModel = viewModel()
    viewModel.workerCall()

    Scaffold(
        topBar = { MainHeader(pageName = "Main") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .size(
                        LocalConfiguration.current.screenWidthDp.dp,
                        (LocalConfiguration.current.screenHeightDp / 3.35).dp
                    )
            ) {
                TreeScreen()
            }

            Column(){

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Your Tasks",
                        fontFamily = FontFamily(Font(R.font.itim)),
                        fontSize = 24.sp
                    )

                    Text(
                        text = "See All",
                        fontFamily = FontFamily(Font(R.font.itim)),
                        style = TextStyle(
                            color = colorResource(id = R.color.app_red)
                        ),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clickable {
                                navHostController.navigate("TaskList")
                            }

                    )

                }


                Column {
                    val viewModel : AppViewModel = viewModel()
                    val tasksItems by viewModel.getTaskList().observeAsState()
                    var count = 2
                    tasksItems?.forEach { item ->
                        if (count > 0) {
                            TaskCard(
                                taskId = item.taskId,
                                TaskHeading = item.taskHeading,
                                Due = item.due,
                                Water = item.water,
                                Fertilizer = item.fertilizer,
                                isDaily = item.isDaily,
                                isWeekly = item.isWeekly,
                                important = item.important
                            )
                            count--
                        }
                    }
                }

            }

        }
    }
}


@Composable
fun TreeScreen() {
    Box(modifier = Modifier.height(20.dp)) {

        AndroidView(factory = {context ->
            SurfaceView(context).apply {
                val surfaceView = this
                var customViewer: CustomViewer = CustomViewer()

                doOnAttach {
                    customViewer.init(context, surfaceView)
                    customViewer.createRenderables("mouse", "mouse 2")
                    customViewer.createIndirectLight("pillars_2k")
                }
            }
        })

    }

}

@Composable
fun HomeMock(){

}

@Preview
@Composable
fun HomePreview(){
    HomeMock()
}