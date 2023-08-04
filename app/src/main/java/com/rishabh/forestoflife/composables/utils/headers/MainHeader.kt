package com.rishabh.forestoflife.composables.utils.headers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.data.MAX_POINTS
import com.rishabh.forestoflife.navigation.Help
import kotlinx.coroutines.delay
import java.util.Calendar


@Composable
fun MainHeader(pageName: String, navHostController: NavHostController){
    
    val viewModel: AppViewModel = viewModel()


    val maxPoints = MAX_POINTS
    val maxTime = 45*60*1000L


    Row(
        modifier = Modifier
            .size(
                width = LocalConfiguration.current.screenWidthDp.dp, height = 70.dp
            )
            .safeContentPadding()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = pageName,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.itim)),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
        )

        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {

            if (pageName == "Home") {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp)
                ) {
                    val points by viewModel.getPoints().observeAsState()
                    var isShaking by remember { mutableStateOf(false) }

                    val infiniteTransition = rememberInfiniteTransition()

                    val shakeAnimationSpec = infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = if (isShaking) 1f else 0f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 100, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    if (points != null) {
                        if (points!! >= maxPoints) {
                            LaunchedEffect(Unit) {
                                isShaking = true
                                delay(200)
                                isShaking = false
                            }
                        }
                    }


                    val shakeOffset = shakeAnimationSpec.value.dp

                    Text(
                        text = if (points != null) points.toString() else "0",
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.itim)),
                        modifier = Modifier
                            .offset(x = shakeOffset, y = shakeOffset)
                    )

                    Text(
                        text = "/$maxPoints",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.itim)),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

            else if (pageName == "Focus") {
                val time by viewModel.getTime().observeAsState()
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp)
                ) {

                    var isShaking by remember { mutableStateOf(false) }

                    val infiniteTransition = rememberInfiniteTransition()

                    val shakeAnimationSpec = infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = if (isShaking) 1f else 0f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 100, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    if (time != null) {
                        if (time!! >= maxTime) {
                            LaunchedEffect(Unit) {
                                isShaking = true
                                delay(200)
                                isShaking = false
                            }
                        }
                    }


                    val shakeOffset = shakeAnimationSpec.value.dp

                    Text(
                        text = if (time != null) (time!!/(60*1000)).toString() else "0",
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.itim)),
                        modifier = Modifier
                            .offset(x = shakeOffset, y = shakeOffset)
                    )
                    val limit = maxTime / (60*1000)
                    Text(
                        text = "/$limit min",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.itim)),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

            else if (pageName == "Profile"){
                IconButton(
                    onClick = {
                        navHostController.navigate(Help.route) {
                        popUpTo(navHostController.graph.findStartDestination().id)
                        launchSingleTop = true
                        }
                    }) {
                    Icon(
                        painterResource(id = R.drawable.help_48px),
                        contentDescription = "Help",
                        tint = if (!isSystemInDarkTheme()) Color.Black else colorResource(id = R.color.dark_mode_icon)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun MainHeaderPreview(){
    //MainHeader("Testing")
}