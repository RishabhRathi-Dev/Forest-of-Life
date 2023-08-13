package com.rishabh.forestoflife.composables.extras

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.headers.AlternateHeader
import com.rishabh.forestoflife.data.DEDUCTION

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
    Card (
        modifier = Modifier.size(200.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_green),
            contentColor = colorResource(id = R.color.app_white)
        )
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = modelName),
                    contentDescription = "ModelPhoto",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
            }
            Text(text = "Needs : $target",textAlign = TextAlign.Center, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth())
        }
    }
}

@Composable
fun HomeHelp() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Home",
            fontSize = 32.sp,
        )

        val homeModels = listOf(R.drawable._00, R.drawable._01, R.drawable._12, R.drawable._22, R.drawable._f22)
        val needPoints = listOf("0 Points", "50 Points", "150 Points", "250 Points", "250 Points & 45 min")

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(homeModels.size) { i ->
                modelContainer(modelName = homeModels[i], target = needPoints[i])
            }
        }
    }
}


@Composable
fun FocusHelp() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Focus",
            fontSize = 32.sp,
        )

        val homeModels = listOf(R.drawable._00, R.drawable._1, R.drawable._2, R.drawable._3)
        val needPoints = listOf("0 Min", "15 Min", "30 Min", "45 Min")

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(homeModels.size) { i ->
                modelContainer(modelName = homeModels[i], target = needPoints[i])
            }
        }
    }
}

@Composable
fun IslandHelp() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Island",
            fontSize = 32.sp,
        )

        val homeModels = listOf(
            R.drawable._000,
            R.drawable._001,
            R.drawable._002,
            R.drawable._003,
            R.drawable._010,
            R.drawable._011,
            R.drawable._012,
            R.drawable._013,
            R.drawable._120,
            R.drawable._121,
            R.drawable._122,
            R.drawable._123,
            R.drawable._220,
            R.drawable._221,
            R.drawable._222,
            R.drawable._223
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

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(homeModels.size) { i ->
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

        Card (
            modifier = Modifier.wrapContentSize().padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.card_green),
                contentColor = colorResource(id = R.color.app_white)
            )
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
                    Text(text = "")
                    Text(text = "Daily")
                    Text(text = "Weekly")
                    Text(text = "Single")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.padding(10.dp)) {
                    Text(text = "Important")
                    Text(text = "25")
                    Text(text = "35")
                    Text(text = "45")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
                    Text(text = "Normal")
                    Text(text = "20")
                    Text(text = "30")
                    Text(text = "35")
                }
            }

            Card (
                modifier = Modifier.wrapContentSize().padding(10.dp),
                elevation = CardDefaults.cardElevation(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.app_yellow),
                    contentColor = colorResource(id = R.color.black)
                )
            ) {
                Text(
                    text = "Daily Changes",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
                )
                Text(
                    text = "- Deduction of "  + DEDUCTION + " Points\n- Focus Time Reset",
                    modifier = Modifier.padding(top = 5.dp, start = 12.dp, end = 10.dp, bottom = 10.dp)
                )
            }
        }

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
            text = "Please create issue at",
            modifier = Modifier.padding(horizontal = 5.dp)
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
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}