package com.rishabh.forestoflife.composables.extras

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.composables.utils.headers.AlternateHeader
import com.rishabh.forestoflife.data.AppViewModel

@Composable
fun CreateTask(navHostController : NavHostController){
    // TODO:: Create Task Form
    Scaffold(
        topBar = { AlternateHeader(pageName = "Create Task", navHostController = navHostController) },
    ) {
        val viewModel: AppViewModel = viewModel()
        viewModel.workerCall()
        Box(
            modifier = Modifier.padding(it)
        ){

        }
    }
}

@Preview
@Composable
fun CreateTaskPreview(){

}
