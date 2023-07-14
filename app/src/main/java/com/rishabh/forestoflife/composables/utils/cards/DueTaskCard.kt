package com.rishabh.forestoflife.composables.utils.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.data.AppViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun DueTaskCard(
    taskId: Long,
    TaskHeading : String,
    Due : Date,
    isDaily : Boolean,
    isWeekly : Boolean,
    important : Boolean
){
    //TODO:: Create customizable Task Card
    val w = (LocalConfiguration.current.screenWidthDp/2 - 20)
    val h = (LocalConfiguration.current.screenHeightDp/5 - 20).dp

    val viewModel : AppViewModel = viewModel()

    Card(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize()
        ,

        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.dark_card_green),
            contentColor = colorResource(id = R.color.app_white)
        )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .wrapContentHeight()
                    .size((1.5 * w).dp, h)

            ) {
                Text(
                    text = TaskHeading,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.itim)),
                    modifier = Modifier
                        .padding(5.dp)
                )

                // Type and Due

                Row(modifier = Modifier
                    .padding(5.dp)) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp)
                    ) {
                        var resulting = ""

                        if (isDaily && isWeekly){
                            resulting = "Error"
                        }

                        else if (isDaily){
                            resulting = "Daily"
                        }

                        else if (isWeekly){
                            resulting = "Weekly"
                        }

                        else {
                            resulting = "One Time"
                        }


                        Text(
                            text = "Type : " + resulting,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.itim))
                        )
                    }

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp)
                    ) {

                        val formatter = SimpleDateFormat("dd.MM.yyyy")
                        val formattedDate = formatter.format(Due)

                        Text(
                            text = "Due : " + formattedDate,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.itim))
                        )
                    }
                }
            }

            DueDeleteButton(viewModel = viewModel, taskId = taskId)

            // Star and Completed Button
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size((0.5 * w).dp, h)
                    .padding(5.dp)
            ) {
                DueImportantToggleButton(taskId, viewModel, important)
                DueCompletedButton(viewModel, taskId = taskId)
            }
        }

    }
}

@Composable
fun DueDeleteButton(viewModel: AppViewModel, taskId: Long){
    IconButton(onClick = {
        viewModel.deleteDueTask(taskId)
    }) {
        Icon(
            painter = painterResource(id = R.drawable.delete_48px),
            contentDescription = "Delete Task",
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun DueCompletedButton(viewModel: AppViewModel, taskId : Long){
    IconButton(
        onClick = {
            viewModel.dueTaskCompleted(taskId = taskId)
        },
        modifier = Modifier
            .size(60.dp)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.done_48px),
            contentDescription = "Completed Task",
            modifier = Modifier.size(75.dp)
        )
    }
}

@Composable
fun DueImportantToggleButton(taskId: Long, viewModel: AppViewModel,important: Boolean) {
    val (isImportant, setImportant) = remember { mutableStateOf(important) }

    IconButton(
        onClick = {
            setImportant(!isImportant)
            viewModel.markAndUnMarkImportantDueTask(taskId)
                  },
        modifier = Modifier
            .size(50.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (isImportant) Color.Yellow else Color.LightGray,
            modifier = Modifier
                .size(75.dp)
        )
    }
}

@Preview
@Composable
fun DueTaskCardPreview(){
    //DueTaskCard(0, "Tesaaadfaeaaaaaaaaaaaaaaaaaaaaaaaaating", Calendar.getInstance().time, 10, 5, true, false, true)
}