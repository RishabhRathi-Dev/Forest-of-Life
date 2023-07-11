package com.rishabh.forestoflife.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rishabh.forestoflife.composables.utils.headers.MainHeader

@Composable
fun Home(){
    // TODO: Create Home
    MainHeader(pageName = "Main", treesCount = 0, waterCount = 0, fertilizerCount = 0)

}

@Preview
@Composable
fun HomePreview(){
    Home()
}