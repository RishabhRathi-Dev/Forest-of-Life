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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.data.Inventory


@Composable
fun MainHeader(pageName: String){

    // TODO:: Add Background
    val viewModel: AppViewModel = viewModel()
    val inventoryItems by viewModel.getInventoryItems().observeAsState()
    var treesCount : Int = 0
    var waterCount : Int = 0
    var fertilizerCount : Int = 0

    inventoryItems?.forEach {
        treesCount = it.trees
        waterCount = it.water
        fertilizerCount = it.fertilizer
    }

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