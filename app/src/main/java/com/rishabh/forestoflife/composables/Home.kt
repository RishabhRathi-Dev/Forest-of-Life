package com.rishabh.forestoflife.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.cards.TaskCard
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import com.rishabh.forestoflife.data.AppViewModel
import java.util.Calendar

@Composable
fun Home(navHostController : NavHostController){
    // TODO: Create Home

    Scaffold(
        topBar = { MainHeader(pageName = "Main") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {
        Modifier.padding(it)
        Column(modifier = Modifier.padding(top=80.dp)){
            val viewModel : AppViewModel = viewModel()
            val tasksItems by viewModel.getTaskList().observeAsState()
            tasksItems?.forEach { item ->
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