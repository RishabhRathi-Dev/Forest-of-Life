package com.rishabh.forestoflife.composables.utils.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.helpers.AutoResizeText
import com.rishabh.forestoflife.composables.utils.helpers.FontSizeRange
import com.rishabh.forestoflife.data.AppViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun TaskCard(
    taskId: Long,
    TaskHeading : String,
    Due : Date,
    Water : Int,
    Fertilizer : Int,
    isDaily : Boolean,
    isWeekly : Boolean,
    important : Boolean
){
    val w = (LocalConfiguration.current.screenWidthDp/2 - 20)
    val h = (LocalConfiguration.current.screenHeightDp/5).dp

    val viewModel : AppViewModel = viewModel()

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .wrapContentSize()
        ,

        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_green),
            contentColor = colorResource(id = R.color.app_white)
        )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .wrapContentHeight()
                    .size((1.5 * w).dp, h)

            ) {

                Column() {
                    AutoResizeText(
                        text = TaskHeading,
                        fontSizeRange = FontSizeRange(
                            min = 16.sp,
                            max = 24.sp,
                        ),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily(Font(R.font.itim)),
                        modifier = Modifier
                            .padding(5.dp)
                    )

                    // Rewards

                    Row(modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)) {
                        // Water
                        //TODO
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 5.dp, end = 5.dp)
                            //.paint(painterResource(id = ), contentScale = ContentScale.FillBounds)
                        ) {
                            Text(
                                text = Water.toString(),
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.itim))
                            )
                        }

                        // Fertilizer
                        //TODO
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 5.dp, end = 5.dp)
                            //.paint(painterResource(id = ), contentScale = ContentScale.FillBounds)
                        ) {
                            Text(
                                text = Fertilizer.toString(),
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.itim))
                            )
                        }
                    }

                }

                // Type and Due

                Row(modifier = Modifier
                    .padding(5.dp)) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp)
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
                            .padding(start = 5.dp)
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

            DeleteButton(viewModel = viewModel, taskId = taskId)

            // Star and Completed Button
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size((0.5 * w).dp, h)
                    .padding(5.dp)
            ) {
                ImportantToggleButton(taskId, viewModel, important, isDaily, isWeekly)
                CompletedButton(viewModel, taskId = taskId, water = Water, fertilizer = Fertilizer)
            }
        }

    }
}

@Composable
fun DeleteButton(viewModel: AppViewModel, taskId: Long){
    IconButton(onClick = {
        viewModel.deleteTask(taskId)
    }) {
        Icon(
            painter = painterResource(id = R.drawable.delete_48px),
            contentDescription = "Delete Task",
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun CompletedButton(viewModel: AppViewModel, taskId : Long, water : Int, fertilizer : Int){
    IconButton(
        onClick = {
            viewModel.taskCompleted(taskId = taskId, waterToAdd = water, fertilizerToAdd = fertilizer)
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
fun ImportantToggleButton(taskId: Long, viewModel: AppViewModel, important: Boolean, isDaily: Boolean, isWeekly: Boolean) {
    val (isImportant, setImportant) = remember { mutableStateOf(important) }
    var water = 0
    var fertilizer = 0

    IconButton(
        onClick = {
            setImportant(!isImportant)

            if(!isImportant){
                if (isDaily){
                    water = 3
                    fertilizer = 1
                }

                else if (isWeekly){
                    water = 5
                    fertilizer = 5
                }

                else {
                    water = 3
                    fertilizer = 3
                }

            } else {
                if (isDaily){
                    water = 2
                    fertilizer = 0
                }

                else if (isWeekly){
                    water = 5
                    fertilizer = 3
                }

                else {
                    water = 3
                    fertilizer = 2
                }
            }

            viewModel.markAndUnMarkImportant(taskId, water, fertilizer)
                  },
        modifier = Modifier
            .size(50.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (isImportant) colorResource(id = R.color.app_yellow) else Color.White,
            modifier = Modifier
                .size(75.dp)
        )
    }
}

@Preview
@Composable
fun TaskCardPreview(){
    TaskCard(0, "adfaeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaatingaaaaaaaaaaaaaaaaaaaaaaaaaa", Calendar.getInstance().time, 10, 5, true, false, true)
}