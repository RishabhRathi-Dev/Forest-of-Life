package com.rishabh.forestoflife.composables

import android.os.SystemClock
import android.view.SurfaceView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import com.rishabh.forestoflife.data.TimerViewModel
import com.rishabh.forestoflife.data.services.TimerServiceManager



@Composable
fun Focus(navHostController : NavHostController){
    // TODO:: Create Focus Page
    Scaffold(
        topBar = { MainHeader(pageName = "Focus") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {

        Column(modifier = Modifier.padding(it)) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp / 2.25).dp)
            ) {

                PlantScreen()

            }

            timer()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timer(){
    var startTime by remember {
        mutableStateOf(0L)
    }

    var endTime by remember {
        mutableStateOf(0L)
    }

    var value by remember {
        mutableStateOf(15)
    }

    var progress by remember {
        mutableStateOf(0f)
    }

    val timerViewModel = TimerViewModel.getInstance(LocalContext.current)

    val elapsedTime by timerViewModel!!.getTimerLiveData().observeAsState(initial = 0L)
    val timerStopped by timerViewModel!!.getStopped().observeAsState(initial = true)

    val targetTime = timerViewModel.getTimerTarget()
    progress = elapsedTime.toFloat()

    val animatedProgress = animateFloatAsState(
        targetValue = progress/endTime,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    val serviceStatus = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current




    PlainTooltipBox(tooltip = { Text("Left to subtract; Right to add") }) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .tooltipAnchor()
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .border(BorderStroke(2.dp, color = Color.Black.copy(alpha = 0.1f)))



        ) {

            if (!timerStopped) {


                //Log.d("Focus", progress.toString() + ";" + elapsedTime.toString() + ";" +targetTime.toString())

                CircularProgressIndicator(
                    progress = animatedProgress,
                    Modifier
                        .size(200.dp)
                        .padding(5.dp),
                    trackColor = colorResource(id = R.color.app_bg),
                    color = colorResource(id = R.color.app_yellow),
                    strokeWidth = 15.dp,
                    strokeCap = StrokeCap.Round
                )
                
                Text(text = formatTime(elapsedTime))

                Text(
                    text = "Stop",
                    style = TextStyle(
                        color = colorResource(id = R.color.app_red),
                        fontFamily = FontFamily(Font(R.font.itim)),
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .offset(y = 20.dp)
                        .clickable {
                            val timerServiceManager =
                                TimerServiceManager(applicationContext = context)

                            if (serviceStatus.value) {
                                // service already running
                                // stop the service
                                serviceStatus.value = !serviceStatus.value
                                timerServiceManager.stopTimerService()

                            } else {
                                // service not running start service.
                                serviceStatus.value = !serviceStatus.value
                                // start
                                timerServiceManager.startTimerService()
                            }
                        }
                )
            } else {

                val w = (LocalConfiguration.current.screenWidthDp/2).dp
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(
                        onClick = {

                            if (value > 15){
                                value -= 15
                            }

                        },
                        modifier = Modifier
                            .size(w, 200.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background,
                            contentColor = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray
                        ),
                        shape = RectangleShape

                    )
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios_48px),
                            contentDescription = "Subtract",
                            modifier = Modifier.offset(x=(-w/4)+5.dp)
                        )
                    }

                    Button(
                        onClick = {

                            if (value < 60){
                                value += 15
                            }

                        },
                        modifier = Modifier
                            .size(w, 200.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background,
                            contentColor = if (isSystemInDarkTheme()) Color.Gray else Color.DarkGray),
                        shape = RectangleShape

                    )
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward_ios_48px),
                            contentDescription = "Add",
                            modifier = Modifier.offset(x=(w/4))
                        )
                    }
                }


                OutlinedButton(
                    onClick = {
                        startTime = SystemClock.elapsedRealtime()
                        endTime = ((value*60000).toLong())
                        timerViewModel?.setTimerTarget(endTime)
                        val timerServiceManager = TimerServiceManager(applicationContext = context)

                        if (serviceStatus.value) {
                            // service already running
                            // stop the service
                            serviceStatus.value = !serviceStatus.value
                            timerServiceManager.stopTimerService()

                        } else {
                            // service not running start service.
                            serviceStatus.value = !serviceStatus.value
                            // start
                            timerServiceManager.startTimerService()
                        }

                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = colorResource(id = R.color.app_white),
                        containerColor = colorResource(id = R.color.card_green)
                    ),
                    border = BorderStroke(0.dp, colorResource(id = R.color.card_green)),
                    modifier = Modifier
                        .size(200.dp)
                        .padding(15.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)

                ) {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = value.toString(),
                            fontSize = 32.sp,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.itim)),
                                color = colorResource(id = R.color.app_white)
                            ),
                        )

                        Text(
                            text = "min",
                            fontSize = 12.sp,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.itim)),
                                color = colorResource(id = R.color.app_white)
                            )
                        )
                    }
                }
            }
        }
    }
}

fun formatTime(timeInMillis: Long): String {
    val seconds = timeInMillis / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}


@Composable
fun PlantScreen(){
    val context = LocalContext.current
    val surfaceView = remember { SurfaceView(context) }
    val customViewer = remember { CustomViewer() }

    // Handle initialization and cleanup with DisposableEffect
    DisposableEffect(surfaceView) {
        customViewer.init(surfaceView.context, surfaceView)
        customViewer.createRenderables("mouse", "mouse 2")
        customViewer.createIndirectLight("pillars_2k")
        customViewer.onResume()

        onDispose {
            customViewer.onPause()
            customViewer.onDestroy()
        }
    }

    // Use ViewCompositionStrategy to control the view's lifecycle
    AndroidView(
        factory = { surfaceView },
        modifier = Modifier.fillMaxSize(), // Adjust the modifier as needed
        update = {}
    )
}


@Preview
@Composable
fun FocusPreview(){
    timer()
}