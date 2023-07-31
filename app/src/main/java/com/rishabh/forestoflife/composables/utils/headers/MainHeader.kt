package com.rishabh.forestoflife.composables.utils.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.data.MAX_POINTS


@Composable
fun MainHeader(pageName: String){
    
    val viewModel: AppViewModel = viewModel()
    val points by viewModel.getPoints().observeAsState()

    val maxPoints = MAX_POINTS


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
            Row(modifier = Modifier
                .wrapContentSize()
                .padding(5.dp)
            ){

                Text(
                    text = if (points != null) points.toString() else "0",
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