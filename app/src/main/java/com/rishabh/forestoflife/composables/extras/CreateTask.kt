package com.rishabh.forestoflife.composables.extras

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.headers.AlternateHeader
import com.rishabh.forestoflife.data.AppViewModel
import com.rishabh.forestoflife.data.Task
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTask(navHostController : NavHostController){
    // TODO:: Create Task Form
    Scaffold(
        topBar = { AlternateHeader(pageName = "Create Task", navHostController = navHostController) },
    ) {
        val viewModel: AppViewModel = viewModel()
        val context = LocalContext.current

        // TextFields (Task Heading) Variables
        var taskHeading by remember { mutableStateOf("") }
        val maxChar = 80

        // Star
        val (isImportant, setImportant) = remember{ mutableStateOf(false) }

        // Repeat CheckBox
        val (checkedState, onStateChange) = remember { mutableStateOf(false) }

        // Daily or Weekly -> Daily : False ; Weekly : True
        var weekly by remember { mutableStateOf(false) }

        // Date
        val state = rememberDatePickerState(initialSelectedDateMillis = Calendar.getInstance().timeInMillis, initialDisplayMode = DisplayMode.Input)

        // Rewards
        var points by remember {
            mutableStateOf(0)
        }


        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
                ) {

                Row {

                    TextField(
                        value = taskHeading,
                        onValueChange = {
                            if (it.length <= maxChar) taskHeading = it
                        },

                        modifier = Modifier.width((LocalConfiguration.current.screenWidthDp*0.75).dp),

                        supportingText = {
                            Text(
                                text = "${taskHeading.length} / $maxChar",
                                textAlign = TextAlign.End,
                            )
                        },
                    )
                }

                IconButton(
                    onClick = {
                        setImportant(!isImportant)
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape = CircleShape)
                        .background(
                            color = colorResource(id = R.color.card_green)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (isImportant) colorResource(id = R.color.app_yellow) else Color.White,
                        modifier = Modifier
                            .size(75.dp)
                            .padding(5.dp)

                    )
                }
            }


            
            // Repeat checkbox with disabled slideswitch
            Row(

                Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .toggleable(
                        value = checkedState,
                        onValueChange = { onStateChange(!checkedState) },
                        role = Role.Checkbox
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = null, // null recommended for accessibility with screenreaders
                    )

                    Text(
                        text = "Repeat",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Daily",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )

                    Switch(
                        modifier = Modifier.semantics { contentDescription = "Daily Or Weekly" },
                        checked = weekly,
                        enabled = checkedState,
                        onCheckedChange = { weekly = it }
                    )

                    Text(
                        text = "Weekly",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }

            }
            
            // Due Date and Importance
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    ,

                ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Pre-select a date for January 4, 2020
                    DatePicker(state = state)
                }
            }

            // Rewards
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape = RoundedCornerShape(10.dp))

                    .background(color = colorResource(id = R.color.card_green))

                ) {
                Text(
                    text = "Rewards",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(10.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.app_bg),
                        fontFamily = FontFamily(Font(R.font.itim))
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if(isImportant){
                        if (checkedState && !weekly){
                            points = 25
                        }

                        else if (checkedState && weekly){
                            points = 35
                        }

                        else {
                            points = 45
                        }

                    } else {
                        if (checkedState && !weekly){
                            points = 20
                        }

                        else if (checkedState && weekly){
                            points = 30
                        }

                        else {
                            points = 35
                        }
                    }

                    Text(
                        text = "Points",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        style = TextStyle(
                            color = colorResource(id = R.color.app_bg),
                            fontFamily = FontFamily(Font(R.font.itim))
                        )
                    )

                    Text(
                        text = points.toString(),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 10.dp, end = 20.dp),
                        style = TextStyle(
                            color = colorResource(id = R.color.app_bg),
                            fontFamily = FontFamily(Font(R.font.itim))
                        )
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = {

                        if (taskHeading.isNotEmpty()) {
                            val cal = (Calendar.getInstance().apply {
                                timeInMillis = state.selectedDateMillis!!
                            }).time
                            val sdf = SimpleDateFormat("dd-MM-yyyy")
                            val task: Task = Task(
                                taskHeading = taskHeading,
                                due = sdf.parse(sdf.format(cal)),
                                isWeekly = checkedState && weekly,
                                isDaily = checkedState && !weekly,
                                important = isImportant,
                                points = points
                            )

                            viewModel.createTask(task)
                            navHostController.popBackStack()
                        }

                        else {
                            mToast(context)
                        }
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.app_yellow), contentColor = Color.Black),
                    elevation = ButtonDefaults.buttonElevation(5.dp)
                ) {
                    Text(
                        text = "Create",
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.itim))
                    )

                }
            }

        }
    }
}

private fun mToast(context: Context){
    Toast.makeText(context, "Empty Task", Toast.LENGTH_LONG).show()
}

@Preview
@Composable
fun CreateTaskPreview(){

}
