package com.rishabh.forestoflife.composables

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Paint.Style
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.hoverable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontSynthesis.Companion.Style
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.LockScreenOrientation
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import kotlinx.coroutines.delay
import java.lang.Math.abs
import java.text.DecimalFormat


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
                    .background(color = Color.Cyan)
            ) {

            }

            timer()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timer(){
    var value by remember {
        mutableStateOf(15)
    }

    var timerStart by remember {
        mutableStateOf(false)
    }

    var progress by remember {
        mutableStateOf(0f)
    }

    var timeElapsed by remember {
        mutableStateOf(0)
    }


    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    var accumulatedDrag = 0f
    val threshold = 18f

    PlainTooltipBox(tooltip = { Text("Left to subtract Right to add") }) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .tooltipAnchor()
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .border(BorderStroke(2.dp, color = Color.Black.copy(alpha = 0.1f)))



        ) {

            LaunchedEffect(timerStart) {
                if (timerStart) {
                    while (timeElapsed < (value * 60)) {
                        delay(1000) // Change interval here (in milliseconds)
                        timeElapsed++
                        progress = (((timeElapsed).toFloat()) / (value * 60))
                        Log.d("Time", progress.toString())
                    }
                }
            }

            if (timerStart) {

                val minutes = timeElapsed / 60
                val seconds = timeElapsed % 60


                startDND()
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
                
                Text(text = String.format("%02d:%02d", minutes, seconds))

                if (timeElapsed >= value * 60){
                    timerStart = false
                    stopDND()
                    timeElapsed = 0
                    progress = 0f
                }

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
                        timerStart = true

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


@Composable
fun startDND(){

    val notificationManager = LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.isNotificationPolicyAccessGranted) {
        Log.d("NM", "has permissions")
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY)

    } else {
        Log.d("NM", "does not have permissions")
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        startActivity(LocalContext.current, intent, null)
    }
}

@Composable
fun stopDND(){
    val notificationManager = LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.isNotificationPolicyAccessGranted) {
        Log.d("NM", "has permissions")
        // Set interruption filter to allow all interruptions
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
    } else {
        Log.d("NM", "does not have permissions")
        // Open the system settings screen for notification policy access
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        startActivity(LocalContext.current, intent, null)
    }

}
@Preview
@Composable
fun FocusPreview(){
    timer()
}