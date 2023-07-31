package com.rishabh.forestoflife.composables.extras

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.cards.DueTaskCard
import com.rishabh.forestoflife.composables.utils.cards.TaskCard
import com.rishabh.forestoflife.composables.utils.headers.AlternateHeader
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.ui.theme.Green

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
                var selectedIndex by remember { mutableStateOf(0) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Filter",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 18.sp
                    )

                    val cornerRadius = 16.dp
                    val white = colorResource(id = R.color.app_white)
                    val darkGreen = colorResource(id = R.color.dark_card_green)
                    val itemsList = listOf<String>("All", "Today", "Important")

                    itemsList.forEachIndexed { index, item ->

                        OutlinedButton(
                            onClick = { selectedIndex = index },
                            modifier = when (index) {
                                0 ->
                                    Modifier
                                        .offset(0.dp, 0.dp)
                                        .zIndex(if (selectedIndex == index) 1f else 0f)
                                else ->
                                    Modifier
                                        .offset((-1 * index).dp, 0.dp)
                                        .zIndex(if (selectedIndex == index) 1f else 0f)
                            },
                            shape = when (index) {
                                0 -> RoundedCornerShape(
                                    topStart = cornerRadius,
                                    topEnd = 0.dp,
                                    bottomStart = cornerRadius,
                                    bottomEnd = 0.dp
                                )
                                itemsList.size - 1 -> RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = cornerRadius,
                                    bottomStart = 0.dp,
                                    bottomEnd = cornerRadius
                                )
                                else -> RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            },
                            border = BorderStroke(
                                1.dp, if (selectedIndex == index) {
                                    Green
                                } else {
                                    Green.copy(alpha = 0.75f)
                                }
                            ),
                            colors = if (selectedIndex == index) {
                                ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else darkGreen.copy(alpha = 0.1f),
                                    contentColor = if (isSystemInDarkTheme()) white else Color.Black
                                )
                            } else {
                                ButtonDefaults.outlinedButtonColors(
                                    containerColor = darkGreen,
                                    contentColor = white
                                )
                            }
                        ) {
                            Text(item)
                        }
                    }
                }

                Text(
                    text = "Current",
                    modifier = Modifier.padding(16.dp)
                )

                Column {
                    val viewModel: AppViewModel = viewModel()

                    if (selectedIndex == 0){
                        val tasksItems by viewModel.getTaskList().observeAsState()
                        tasksItems?.forEach { item ->

                            TaskCard(
                                taskId = item.taskId,
                                TaskHeading = item.taskHeading,
                                Due = item.due,
                                Points = item.points,
                                isDaily = item.isDaily,
                                isWeekly = item.isWeekly,
                                important = item.important
                            )
                        }
                    }

                    else if (selectedIndex == 1){
                        val tasksItems by viewModel.getTodayTaskList().observeAsState()
                        tasksItems?.forEach { item ->

                            TaskCard(
                                taskId = item.taskId,
                                TaskHeading = item.taskHeading,
                                Due = item.due,
                                Points = item.points,
                                isDaily = item.isDaily,
                                isWeekly = item.isWeekly,
                                important = item.important
                            )
                        }
                    }

                    else if (selectedIndex == 2) {
                        val tasksItems by viewModel.getImportantTaskList().observeAsState()
                        tasksItems?.forEach { item ->

                            TaskCard(
                                taskId = item.taskId,
                                TaskHeading = item.taskHeading,
                                Due = item.due,
                                Points = item.points,
                                isDaily = item.isDaily,
                                isWeekly = item.isWeekly,
                                important = item.important
                            )
                        }
                    }


                }

                // Past Due
                if (selectedIndex == 0 || selectedIndex == 2) {
                    Text(
                        text = "Past Due",
                        modifier = Modifier.padding(16.dp)
                    )


                    Column {
                        val viewModel: AppViewModel = viewModel()


                        if (selectedIndex == 0) {
                            val dueTaskItems by viewModel.getDueTaskList().observeAsState()
                            dueTaskItems?.forEach { item ->
                                DueTaskCard(
                                    taskId = item.taskId,
                                    TaskHeading = item.taskHeading,
                                    Due = item.due,
                                    isDaily = item.isDaily,
                                    isWeekly = item.isWeekly,
                                    important = item.important
                                )
                            }
                        } else if (selectedIndex == 2) {
                            val dueTaskItems by viewModel.getImportantDueTaskList().observeAsState()
                            dueTaskItems?.forEach { item ->
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
    }
}

@Preview
@Composable
fun TaskListPreview(){

}