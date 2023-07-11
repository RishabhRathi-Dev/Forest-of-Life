package com.rishabh.forestoflife.composables.utils.headers

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rishabh.forestoflife.R


@Composable
fun MainHeader(pageName: String){
    //TODO: Create Main Header which goes in home, focus, island, profile
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
    val waterCount = sharedPreferences.getInt("water", 0)
    val treesCount = sharedPreferences.getInt("tree", 0)
    val fertilizerCount = sharedPreferences.getInt("fertilizer", 0)
    Row(
        modifier = Modifier
            .size(
                width = LocalConfiguration.current.screenWidthDp.dp, height = 70.dp
            )
            .safeContentPadding()
            .background(color = MaterialTheme.colorScheme.background)
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
            // Trees in island
            Box(modifier = Modifier
                .wrapContentSize()
                .padding(5.dp)
                //.paint(painterResource(id = ), contentScale = ContentScale.FillBounds)
            ){
                Text(
                    text = treesCount.toString(),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.itim))
                )
            }
            //Icon(painter = painterResource(id = R.drawable), contentDescription = "Test")

            // Water
            Box(modifier = Modifier
                .wrapContentSize()
                .padding(5.dp)
                //.paint(painterResource(id = ), contentScale = ContentScale.FillBounds)
            ){
                Text(
                    text = waterCount.toString(),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.itim))
                )
            }

            // Fertilizer
            Box(modifier = Modifier
                .wrapContentSize()
                .padding(5.dp)
                //.paint(painterResource(id = ), contentScale = ContentScale.FillBounds)
            ){
                Text(
                    text = fertilizerCount.toString(),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.itim))
                )
            }
        }

    }
}

@Preview
@Composable
fun MainHeaderPreview(){
    MainHeader("Testing")
}