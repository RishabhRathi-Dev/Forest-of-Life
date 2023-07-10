package com.rishabh.forestoflife.composables.utils.headers

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun MainHeader(pageName: String){
    //TODO: Create Main Header which goes in home, focus, island, profile
    Row() {
        Text(text = pageName)

        // Trees in island
        //Icon(painter = painterResource(id = R.drawable), contentDescription = "Test")

        // Water
        //Icon(painter = painterResource(id = R.drawable), contentDescription = "Test")

        // Fertilizer
        //Icon(painter = painterResource(id = R.drawable), contentDescription = "Test")
    }
}

@Preview
@Composable
fun MainHeaderPreview(){
    MainHeader("Testing")
}