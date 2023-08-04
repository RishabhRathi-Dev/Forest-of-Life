package com.rishabh.forestoflife.composables

import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
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
import androidx.compose.material.LinearProgressIndicator
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
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.data.MAX_POINTS
import com.rishabh.forestoflife.data.MAX_TIME
import com.rishabh.forestoflife.data.TimerViewModel
import com.rishabh.forestoflife.data.services.TimerServiceManager
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

@Composable
fun Focus(navHostController : NavHostController){
    // TODO:: Create Focus Page
    Scaffold(
        topBar = { MainHeader(pageName = "Focus", navHostController = navHostController) },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {

        Column(modifier = Modifier.padding(it)) {
            val viewModel : AppViewModel = viewModel()
            val time by viewModel.getTime().observeAsState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp / 2.25).dp)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {

                PlantScreen()

            }

            Box{
                Box(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    timer()
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .offset(x = 0.dp, y = (-18).dp),
                ) {
                    if (time != null) {
                        val sharedPreferences = LocalContext.current.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
                        val sdf = SimpleDateFormat("dd-MM-yyyy")
                        val today = sdf.parse(sdf.format(Calendar.getInstance().time))
                        val last = sdf.parse(sharedPreferences.getString("DayFocusCelebrated", "01-01-1970"))


                        var progress = (time!!).toFloat() / MAX_TIME
                        if (progress < 0){
                            progress = 0f
                        }

                        val celebrate = last.before(today) && (progress>=1.0f)

                        LinearProgressIndicator(
                            progress = progress,
                            color = colorResource(id = R.color.card_green),
                            backgroundColor = colorResource(id = R.color.app_yellow),
                            strokeCap = StrokeCap.Square,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )

                        if (celebrate){

                            val party = Party(
                                speed = 0f,
                                maxSpeed = 15f,
                                damping = 0.9f,
                                angle = Angle.BOTTOM,
                                spread = Spread.ROUND,
                                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                                emitter = Emitter(duration = 3, TimeUnit.SECONDS).perSecond(100),
                                position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0))
                            )

                            Box{
                                KonfettiView(
                                    parties = listOf(party),
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }

                            // Closing Party
                            val editor = sharedPreferences.edit()
                            editor.putString("DayFocusCelebrated", sdf.format(Calendar.getInstance().time))
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                                editor.apply()
                            }
                        }
                    }
                }


            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timer(){
    var startTime by remember {
        mutableStateOf(0L)
    }

    val sharedPreferences = LocalContext.current.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)

    var endTime by remember {
        mutableStateOf(sharedPreferences.getLong("CurrentEndTime", 0L))
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

    val sharedPreferences = context.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
    val graphic = sharedPreferences.getInt("Graphics", 2)

    val appViewModel : AppViewModel = viewModel()

    val time by appViewModel.getTime().observeAsState()

    var modelName = when (time) {
        in 0L..15*60*1000 -> {
            "00"
        }
        in (15*60*1000L+1)..30*60*1000L -> {
            "1"
        }
        in 30*60*1000L+1..45*60*1000L - 1000 ->{
            "2"
        }
        else -> {
            "3"
        }
    }


    /*
     DisposableEffect is used to handle the initialization and cleanup of the CustomViewer,
     ensuring that it starts and stops correctly with the Composable function.
     */

    // Handle initialization and cleanup with DisposableEffect
    if (time != null){
        DisposableEffect(surfaceView, modelName) {
            Log.d("Time", time.toString())
            customViewer.init(surfaceView.context, surfaceView)
            when (graphic){
                0 -> {
                    customViewer.lowSetting()
                }

                1 -> {
                    customViewer.mediumSetting()
                }

                2 -> {
                    customViewer.highSetting()
                }

                3 -> {
                    customViewer.ultraSetting()
                }

                else -> {
                    customViewer.mediumSetting()
                }
            }
            customViewer.createRenderables("focus", modelName)
            customViewer.createIndirectLight("pillars_2k")
            customViewer.onResume()

            onDispose {
                customViewer.onPause()
                customViewer.onDestroy()
            }
        }
    }

    // Use ViewCompositionStrategy to control the view's lifecycle
    AndroidView(
        factory = { surfaceView },
        modifier = Modifier.fillMaxSize(),
        update = {}
    )
}


@Preview
@Composable
fun FocusPreview(){
    timer()
}