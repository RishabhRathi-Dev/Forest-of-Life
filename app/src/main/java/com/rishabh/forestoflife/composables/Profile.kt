package com.rishabh.forestoflife.composables

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader

@Composable
fun Profile(navHostController : NavHostController){
    //TODO:: Create Profile Page
    Scaffold(
        topBar = { MainHeader(pageName = "Profile") },
        bottomBar = { BottomBar(navController = navHostController) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {

            val sharedPreferences = LocalContext.current.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
            var selectedImageUri by remember {
                mutableStateOf(sharedPreferences.getInt("profile", R.drawable.person_48px))
            }

            // Photo, Name,
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                var expanded by remember { mutableStateOf(false) }
                val pictureList = listOf(
                    R.drawable.person_48px,
                    R.drawable.star_48px
                    // TODO:: Add more image resource IDs here
                )

                IconButton(
                    onClick = {
                        expanded = true
                    },

                    modifier = Modifier
                        .size(150.dp)
                        .clip(shape = CircleShape)
                        .background(
                            color = if (isSystemInDarkTheme())
                                        colorResource(id = R.color.app_white).copy(alpha = 0.5f)
                                    else
                                        colorResource(id = R.color.card_green).copy(alpha = 0.1f)
                        )

                ) {
                    Icon(
                        painter = painterResource(id = selectedImageUri),
                        contentDescription = "Profile",
                        modifier = Modifier.size(100.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = (LocalConfiguration.current.screenWidthDp / 2).dp)
                ) {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(color = if (isSystemInDarkTheme())
                                Color.LightGray.copy(alpha = 0.9f)
                            else
                                colorResource(id = R.color.card_green).copy(alpha = 0.15f)
                            )
                    ) {
                        pictureList.forEach { picture ->
                            DropdownMenuItem(
                                onClick = {
                                    val editor = sharedPreferences.edit()
                                    editor.putInt("profile", picture)
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                                        editor.apply()
                                    }
                                    selectedImageUri = picture
                                    expanded = false
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = picture),
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    }
                }

                sharedPreferences.getString("name", "Anon")?.let {
                        it1 ->
                    Text(
                        text = it1,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .padding(16.dp)

                    )
                }

            }

            // Details
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Trees Grown : "
                    )

                    sharedPreferences.getString("TreesGrown", "0")?.let { it1 ->
                        Text(
                            text = it1
                        )
                    }
                }

                // Flowers Grown
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Flowers Grown : "
                    )

                    sharedPreferences.getString("FlowersGrown", "0")?.let { it1 ->
                        Text(
                            text = it1
                        )
                    }
                }

                // Total Focus
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Focus Time : "
                    )

                    sharedPreferences.getString("TotalFocusTime", "00:00")?.let { it1 ->
                        Text(
                            text = it1
                        )
                    }
                }
            }


        }
    }
}

@Preview
@Composable
fun ProfilePreview(){

}