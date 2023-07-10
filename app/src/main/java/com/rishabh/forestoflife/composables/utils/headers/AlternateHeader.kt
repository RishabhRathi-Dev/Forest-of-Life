package com.rishabh.forestoflife.composables.utils.headers

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AlternateHeader(pageName : String){
    // TODO:: Create Alternate Header that goes on Task List and Create
    Row() {
        // Back Button
        //Icon(painter = painterResource(id = R.drawable), contentDescription = "Test")

        Text(text = pageName)
    }
}

@Preview
@Composable
fun AlternateHeaderPreview(){
    AlternateHeader("Testing")
}