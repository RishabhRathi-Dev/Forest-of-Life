package com.rishabh.forestoflife.composables

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.cards.TaskCard
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import com.rishabh.forestoflife.data.AppViewModel
import java.util.Calendar

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
                    .size(LocalConfiguration.current.screenWidthDp.dp, (LocalConfiguration.current.screenHeightDp/3.5).dp)
                    .background(Color.Blue)
            ) {

            }

            Column(){

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
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
fun HomeMock(){

}

@Preview
@Composable
fun HomePreview(){
    HomeMock()
}