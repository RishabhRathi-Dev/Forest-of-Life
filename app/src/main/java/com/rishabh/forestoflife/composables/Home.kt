package com.rishabh.forestoflife.composables

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.SurfaceView
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.cards.TaskCard
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.data.MAX_POINTS
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.PartySystem
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit


@Composable
fun Home(navHostController : NavHostController){
    // TODO: Create Home

    val viewModel : AppViewModel = viewModel()
    viewModel.workerCall()

    Scaffold(
        topBar = { MainHeader(pageName = "Home", navHostController = navHostController) },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {
        val point by viewModel.getPoints().observeAsState()

        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .size(
                        LocalConfiguration.current.screenWidthDp.dp,
                        (LocalConfiguration.current.screenHeightDp / 3.35).dp
                    )
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {

                TreeScreen()
            }

            Box{

                Column(modifier = Modifier.verticalScroll(rememberScrollState())){

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Your Tasks",
                            fontFamily = FontFamily(Font(R.font.itim)),
                            fontSize = 24.sp
                        )

                        Text(
                            text = "See All",
                            fontFamily = FontFamily(Font(R.font.itim)),
                            style = TextStyle(
                                color = colorResource(id = R.color.app_red)
                            ),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .clickable {
                                    navHostController.navigate("TaskList")
                                }

                        )

                    }


                    Column {
                        val viewModel : AppViewModel = viewModel()
                        val tasksItems by viewModel.getTaskList().observeAsState()
                        var count = 2
                        tasksItems?.forEach { item ->
                            if (count > 0) {
                                TaskCard(
                                    taskId = item.taskId,
                                    TaskHeading = item.taskHeading,
                                    Due = item.due,
                                    Points = item.points,
                                    isDaily = item.isDaily,
                                    isWeekly = item.isWeekly,
                                    important = item.important
                                )
                                count--
                            }
                        }
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .offset(x = 0.dp, y = (-8).dp),
                ) {
                    if (point != null) {
                        val sharedPreferences = LocalContext.current.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
                        val sdf = SimpleDateFormat("dd-MM-yyyy")
                        val today = sdf.parse(sdf.format(Calendar.getInstance().time))
                        val last = sdf.parse(sharedPreferences.getString("DayHomeCelebrated", "01-01-1970"))


                        var progress = (point!!).toFloat() / MAX_POINTS
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
                            editor.putString("DayHomeCelebrated", sdf.format(Calendar.getInstance().time))
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


@Composable
fun TreeScreen() {
    val context = LocalContext.current
    val surfaceView = remember { SurfaceView(context) }
    val customViewer = remember { CustomViewer() }

    val appViewModel: AppViewModel = viewModel()

    val point by appViewModel.getPoints().observeAsState()
    val time by appViewModel.getTime().observeAsState()

    val sharedPreferences = context.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
    val graphic = sharedPreferences.getInt("Graphics", 2)

    var modelName = when (point) {
        in 0..50 -> {
            "00"
        }

        in 51..150 -> {
            "01"
        }

        in 151..250 -> {
            "12"
        }

        in 251..350 -> {
            "22"
        }

        else -> {
            "00"
        }
    }

    if (time != null) {
        // 60 min of focus time and more than 250 points then special model
        if (time!! > 45 * 60 * 1000 - 1000 && point!! > 250) {
            modelName = "F$modelName"
        }
    }

    /*
     DisposableEffect is used to handle the initialization and cleanup of the CustomViewer,
     ensuring that it starts and stops correctly with the Composable function.
     */

    // Handle initialization and cleanup with DisposableEffect
    if (point != null) {
        DisposableEffect(surfaceView, modelName) {
            Log.d("Point", point.toString())
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
            customViewer.createRenderables("home", modelName)
            customViewer.createIndirectLight("pillars_2k")
            customViewer.onResume()

            onDispose {
                customViewer.onPause()
                customViewer.onDestroy()
            }
        }
    }

    Column {
        // Use ViewCompositionStrategy to control the view's lifecycle
        AndroidView(
            factory = { surfaceView },
            modifier = Modifier.fillMaxSize(),
            update = {}
        )
    }
}
