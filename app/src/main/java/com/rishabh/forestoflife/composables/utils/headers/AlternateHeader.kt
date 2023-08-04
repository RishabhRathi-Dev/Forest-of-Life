package com.rishabh.forestoflife.composables.utils.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R

@Composable
fun AlternateHeader(pageName : String, navHostController: NavHostController){

    Row(
        modifier = Modifier
            .size(
                width = LocalConfiguration.current.screenWidthDp.dp, height = 70.dp
            )
            .safeContentPadding()
        ,
        //verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        Button(
            modifier = Modifier
                .wrapContentSize()
                .offset(x = (-15).dp)
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent ,
                contentColor = if(isSystemInDarkTheme()) colorResource(id = R.color.dark_mode_icon) else Color.Black),
            onClick = {

                navHostController.popBackStack()

            }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_48px),
                contentDescription = "Test",
                modifier = Modifier.size(50.dp)
            )
        }

        Text(
            text = pageName,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.itim)),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .size(width = LocalConfiguration.current.screenWidthDp.dp, height = 70.dp)
                .offset(x = (-40).dp, y = 10.dp)
        )
    }
}

@Preview
@Composable
fun AlternateHeaderPreview(){
    //AlternateHeader("Testing")
}