package com.rishabh.forestoflife.composables.extras

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.cards.DueTaskCard
import com.rishabh.forestoflife.composables.utils.cards.TaskCard
import com.rishabh.forestoflife.composables.utils.headers.AlternateHeader
import com.rishabh.forestoflife.data.AppViewModel

@Composable
fun TaskList(navHostController: NavHostController){
    //TODO: Create see all task list
    Scaffold(
        topBar = { AlternateHeader(pageName = "Tasks List", navHostController = navHostController) },
    ) {
        val viewModel : AppViewModel = viewModel()
        viewModel.workerCall()
        Box(
            modifier = Modifier.padding(it)
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                // Running
                Text(
                    text = "Current",
                    modifier = Modifier.padding(16.dp)
                )

                Column() {
                    val viewModel: AppViewModel = viewModel()
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

                // Past Due
                Text(
                    text = "Past Due",
                    modifier = Modifier.padding(16.dp)
                )

                Column {
                    val viewModel: AppViewModel = viewModel()
                    val dueTaskItems by viewModel.getDueTaskList().observeAsState()

                    dueTaskItems?.forEach {item ->
                        DueTaskCard(
                            taskId = item.taskId,
                            TaskHeading = item.taskHeading,
                            Due = item.due,
                            isDaily = item.isDaily,
                            isWeekly = item.isWeekly,
                            important = item.important
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun TaskListPreview(){

}