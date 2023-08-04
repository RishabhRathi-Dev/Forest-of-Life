package com.rishabh.forestoflife.composables.extras

import android.util.Log
import android.view.SurfaceView
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.CustomViewer
import com.rishabh.forestoflife.composables.utils.headers.AlternateHeader

@Composable
fun Help(navHostController:NavHostController){
    Scaffold(
        topBar = { AlternateHeader(pageName = "Help", navHostController = navHostController) },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ){
            HomeHelp()
            FocusHelp()
            IslandHelp()
            RewardsHelp()
            BugsIssuesHelp()
        }
    }

}


@Composable
fun modelContainer(modelName : Int, target : String){
    Column (
        modifier = Modifier.width(200.dp)
    ) {
        Image(
            painter = painterResource(id = modelName),
            contentDescription = "ModelPhoto",
            contentScale = ContentScale.Fit
        )
        Text(text = "Needs : $target")
    }
}

@Composable
fun HomeHelp(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Home",
            fontSize = 32.sp,
        )

        val context = LocalContext.current
        val homeModels = listOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground)
        val needPoints = listOf("0 Points", "50 Points", "150 Points", "250 Points", "250 Points & 45 min")

        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            for (i in 0..4){
                modelContainer(modelName = homeModels[i], target = needPoints[i])
            }
        }
    }

}

@Composable()
fun FocusHelp(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Focus",
            fontSize = 32.sp,
        )

        val context = LocalContext.current
        val homeModels = listOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground)
        val needPoints = listOf("0 Min", "15 Min", "30 Min", "45 Min")

        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            for (i in 0..3){
                modelContainer(modelName = homeModels[i], target = needPoints[i])
            }
        }
    }
}

@Composable
fun IslandHelp(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Island",
            fontSize = 32.sp,
        )

        val context = LocalContext.current

        val homeModels = listOf(
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground
        )

        val needPoints = listOf(
            "0 Points, 0 Min",
            "0 Points, 15 Min",
            "0 Points, 30 Min",
            "0 Points, 45 Min",
            "50 Points, 0 Min",
            "50 Points, 15 Min",
            "50 Points, 30 Min",
            "50 Points, 45 Min",
            "150 Points, 0 Min",
            "150 Points, 15 Min",
            "150 Points, 30 Min",
            "150 Points, 45 Min",
            "250 Points, 0 Min",
            "250 Points, 15 Min",
            "250 Points, 30 Min",
            "250 Points, 45 Min"
        )

        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            for (i in 0..15){
                modelContainer(modelName = homeModels[i], target = needPoints[i])
            }
        }
    }
}

@Composable
fun RewardsHelp(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Rewards",
            fontSize = 32.sp,
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column() {
                Text(text = "")
                Text(text = "Daily")
                Text(text = "Weekly")
                Text(text = "Single")
            }

            Column() {
                Text(text = "Important")
                Text(text = "25")
                Text(text = "30")
                Text(text = "40")
            }

            Column() {
                Text(text = "Normal")
                Text(text = "15")
                Text(text = "20")
                Text(text = "30")
            }
        }
        Text(
            text = "Daily Changes",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "- Deduction of 120 Points\n- Focus Time Reset",
            modifier = Modifier.padding(top = 5.dp)
        )

    }

}

@Composable
fun BugsIssuesHelp(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Other Issues and Bugs",
            fontSize = 32.sp,
        )
        Text(
            text = "Please create issue at"
        )
        val uriHandler = LocalUriHandler.current
        val link = "https://github.com/RishabhRathi-Dev/Forest-of-Life"
        val annonString : AnnotatedString = buildAnnotatedString {

            val str = link
            append(str)
            addStyle(
                style = SpanStyle(
                    color = Color(0xff64B5F6),
                    fontSize = 18.sp,
                    textDecoration = TextDecoration.Underline
                ), start = 0, end = link.length
            )

            addStringAnnotation(
                tag = "URL",
                annotation = "https://github.com/RishabhRathi-Dev/Forest-of-Life",
                start = 0,
                end = link.length
            )
        }
        ClickableText(
            text = annonString,
            onClick = {
                annonString.getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            }
        )
    }
}