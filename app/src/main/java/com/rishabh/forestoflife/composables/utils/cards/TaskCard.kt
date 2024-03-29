package com.rishabh.forestoflife.composables.utils.cards

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Build
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.rishabh.forestoflife.composables.utils.helpers.SoundPlayer
import com.rishabh.forestoflife.composables.utils.helpers.SoundPlayerListener
import com.rishabh.forestoflife.data.AppViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


@Composable
fun TaskCard(
    taskId: Long,
    TaskHeading : String,
    Due : Date,
    Points : Int,
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
            val sharedPreferences = LocalContext.current.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
            var lefty by remember {
                mutableStateOf(sharedPreferences.getBoolean("LeftHandMode", false))
            }
            if (lefty) {
                // Star and Completed Button
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .size((0.28 * w).dp, h)
                        .padding(5.dp)
                ) {
                    ImportantToggleButton(taskId, viewModel, important, isDaily, isWeekly)
                    CompletedButton(viewModel, taskId = taskId, points = Points)
                }

                DeleteButton(viewModel = viewModel, taskId = taskId)

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .wrapContentHeight()
                        .size((1.72 * w).dp, h)

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
                            Row(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(start = 5.dp, end = 5.dp)
                            ) {

                                Text(
                                    text = "Points : ",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.itim))
                                )

                                Text(
                                    text = Points.toString(),
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
                                resulting = "Single"
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


            } else {
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
                            Row(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(start = 5.dp, end = 5.dp)
                            ) {

                                Text(
                                    text = "Points : ",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.itim))
                                )

                                Text(
                                    text = Points.toString(),
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
                                resulting = "Single"
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
                    CompletedButton(viewModel, taskId = taskId, points = Points)
                }
            }

        }

    }
}

@Composable
fun DeleteButton(viewModel: AppViewModel, taskId: Long){
    val context = LocalContext.current
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
fun CompletedButton(viewModel: AppViewModel, taskId : Long, points : Int){
    val context = LocalContext.current
    IconButton(
        onClick = {
            viewModel.taskCompleted(taskId = taskId, points)
            val sharedPreferences = context.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("TotalPoints", sharedPreferences.getInt("TotalPoints", 0) + points)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply()
            }

            val soundPlayer = SoundPlayer()
            soundPlayer.playSound(context, "sound/decidemp3-14575.mp3")
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
    var points = 0

    IconButton(
        onClick = {
            setImportant(!isImportant)

            if(!isImportant){
                if (isDaily){
                    points = 25
                }

                else if (isWeekly){
                    points = 35
                }

                else {
                    points = 45
                }

            } else {
                if (isDaily){
                    points = 20
                }

                else if (isWeekly){
                    points = 30
                }

                else {
                    points = 35
                }
            }

            viewModel.markAndUnMarkImportant(taskId, points)
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
    TaskCard(0, "adfaeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaatingaaaaaaaaaaaaaaaaaaaaaaaaaa", Calendar.getInstance().time, 10, true, true, false)
}