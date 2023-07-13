package com.rishabh.forestoflife.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.cards.TaskCard
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import java.util.Calendar

@Composable
fun Home(navHostController : NavHostController){
    // TODO: Create Home

    Scaffold(
        topBar = { MainHeader(pageName = "Main") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {
        Modifier.padding(it)
        Box(modifier = Modifier.padding(top=80.dp)){
            TaskCard(
                TaskHeading = "test",
                Due = Calendar.getInstance().time,
                Water = 10,
                Fertilizer = 10,
                isDaily = true,
                isWeekly = false,
                important = false
            )
        }

    }
}

@Composable
fun HomeMock(){

}

@Preview
@Composable
fun HomePreview(){
    HomeMock()
}