package com.rishabh.forestoflife.composables

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.data.AppViewModel

@Composable
fun Onboarding2(navHostController: NavHostController){
    val context = LocalContext.current
    val viewModel: AppViewModel = viewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFDFFFE),
                        Color(0xFFEBF4EA)
                    )
                )
            ),

        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp * 0.85).dp)
                .wrapContentHeight()
                .background(
                    color = Color(0x0D789F65),
                    shape = RoundedCornerShape(size = 20.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp * 0.85).dp)
                    .height((LocalConfiguration.current.screenHeightDp * 0.45).dp)
                    .clip(
                        shape = RoundedCornerShape(
                            bottomStart = (LocalConfiguration.current.screenWidthDp * 0.5).dp,
                            bottomEnd = (LocalConfiguration.current.screenWidthDp * 0.5).dp
                        )
                    )

            ) {
                Image(
                    // Placeholder
                    painter = painterResource(R.mipmap._64903),
                    contentDescription = "Your Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = "Hello,",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.itim)),
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.card_green),
                    letterSpacing = 0.6.sp,
                ),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            )

            Text(
                text = "How should we address you?",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.itim)),
                    fontWeight = FontWeight(400),
                    letterSpacing = 0.6.sp,
                ),
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 16.dp, end = 16.dp)
            )

            val nameState = remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
            ) {

                OutlinedTextField(
                    value = nameState.value,
                    onValueChange = { nameState.value = it },
                    label = { androidx.compose.material.Text("Name") },
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .fillMaxWidth()
                    ,

                    shape = RoundedCornerShape(8.dp),
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        //Toast.makeText(mContext, "This is a Circular Button with a + Icon", Toast.LENGTH_LONG).show()
                        //navHostController.navigate("Home")
                        val allFieldsFilled = nameState.value.isNotBlank()

                        if (allFieldsFilled) {
                            saveUserInformation(nameState.value, context, viewModel)

                            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                            ContextCompat.startActivity(context, intent, null)

                            // Navigate to Home screen
                            navHostController.navigate("Home") {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                    inclusive = true // to delete previous stack
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        } else {
                            mToast(context)
                        }
                    },
                    shape = CircleShape,
                    modifier= Modifier.wrapContentSize(),
                    border= BorderStroke(0.dp, Color(MaterialTheme.colorScheme.background.hashCode())),
                    contentPadding = PaddingValues(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colorResource(id = R.color.card_green), 
                        contentColor =  colorResource(id = R.color.app_bg)
                    ),
                    
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
                ) {

                    Text(
                        text = "Get Started",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.itim)),
                            fontWeight = FontWeight(700),
                            letterSpacing = 0.4.sp,
                        ),
                        modifier = Modifier
                            .padding(10.dp)
                    )

                    Icon(
                        painterResource(id = R.drawable.arrow_forward_48px) ,
                        contentDescription = "content description",
                        tint= colorResource(id = R.color.app_bg),
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    )
                }

            }

        }
    }
}

private fun mToast(context: Context){
    Toast.makeText(context, "Registration unsuccessful. Please enter all data correctly.", Toast.LENGTH_LONG).show()
}

private fun saveUserInformation(name: String, context: Context, viewModel: AppViewModel) {
    val sharedPreferences = context.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)

    val editor = sharedPreferences.edit()
    editor.putString("name", name)
    editor.putBoolean("userRegistered", true)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
        editor.apply()
    }

    viewModel.initialSetup()
}

@Preview
@Composable
fun Onboarding2Preview(){
    //Onboarding2()
}