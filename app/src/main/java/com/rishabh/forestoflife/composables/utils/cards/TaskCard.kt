package com.rishabh.forestoflife.composables.utils.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rishabh.forestoflife.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun TaskCard(
    TaskHeading : String,
    Due : Date,
    Water : Int,
    Fertilizer : Int,
    isDaily : Boolean,
    isWeekly : Boolean,
    important : Boolean
){
    //TODO:: Create customizable Task Card
    val w = (LocalConfiguration.current.screenWidthDp/2 - 20)
    val h = (LocalConfiguration.current.screenHeightDp/5 - 20).dp
    Card(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize()
        ,

        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .wrapContentHeight()
                    .size((1.5*w).dp, h)

            ) {
                Text(
                    text = TaskHeading,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.itim)),
                    modifier = Modifier
                        .padding(5.dp)
                )

                // Rewards

                Row(modifier = Modifier
                    .padding(5.dp)) {
                    // Water
                    //TODO
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp)
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
                            .padding(5.dp)
                        //.paint(painterResource(id = ), contentScale = ContentScale.FillBounds)
                    ) {
                        Text(
                            text = Fertilizer.toString(),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.itim))
                        )
                    }
                }

                // Type and Due

                Row(modifier = Modifier
                    .padding(5.dp)) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp)
                    ) {
                        var resulting = ""

                        if (isDaily && isWeekly){
                            resulting = "Error"
                        }

                        if (isDaily){
                            resulting = "Daily"
                        }

                        if (isWeekly){
                            resulting = "Weekly"
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
                            .padding(5.dp)
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

            // Star and Completed Button
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.size((0.5*w).dp, h).padding(5.dp)
            ) {
                ImportantToggleButton(important)
                CompletedButton()
            }
        }

    }
}

@Composable
fun CompletedButton(){
    IconButton(
        onClick = { /*TODO*/ },
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
fun ImportantToggleButton(important: Boolean) {
    val (isImportant, setImportant) = remember { mutableStateOf(important) }

    IconButton(
        onClick = {
            setImportant(!isImportant)
            //TODO : Change in database
                  },
        modifier = Modifier
            .size(50.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (isImportant) Color.Yellow else Color.LightGray,
            modifier = Modifier
                .size(75.dp)
        )
    }
}

@Preview
@Composable
fun TaskCardPreview(){
    TaskCard("Tesaaadfaeaaaaaaaaaaaaaaaaaaaaaaaaating", Calendar.getInstance().time, 10, 5, true, false, true)
}