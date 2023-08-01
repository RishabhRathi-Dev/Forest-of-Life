package com.rishabh.forestoflife.composables

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
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
import androidx.compose.material.Switch
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.bottom.BottomBar
import com.rishabh.forestoflife.composables.utils.headers.MainHeader
import java.time.format.TextStyle

@OptIn(ExperimentalPermissionsApi::class)
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

            val textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.itim)),
            )

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
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Total Points Earned",
                        style = textStyle
                    )

                    Text(
                        text = ":",
                        style = textStyle
                    )

                    sharedPreferences.getInt("TotalPoints", 0)?.let { it1 ->
                        Text(
                            text = it1.toString(),
                            style = textStyle
                        )
                    }
                }

                // Total Focus
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Total Focus Time",
                        style = textStyle
                    )

                    Text(
                        text = ":",
                        style = textStyle
                    )

                    sharedPreferences.getLong("TotalFocusTime", 0L)?.let { it1 ->
                        Text(
                            text = formatTime(it1),
                            style = textStyle
                        )
                    }
                }
            }
            
            // Settings
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Text(
                    text = "Settings",
                    fontSize = 32.sp,
                )
                // Notification
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    val notificationManager = LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    var noti by remember { mutableStateOf(notificationManager.areNotificationsEnabled()) }

                    val notificationPermissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)

                    val context = LocalContext.current
                    Text(
                        text = "Notification",
                        style = textStyle
                    )

                    androidx.compose.material3.Switch(
                        modifier = Modifier.semantics { contentDescription = "Notification" },
                        checked = noti,
                        onCheckedChange = {
                            if (!noti){
                                notificationPermissionState.launchPermissionRequest()
                            } else {
                                val intent = Intent(Settings.ACTION_ALL_APPS_NOTIFICATION_SETTINGS)
                                ContextCompat.startActivity(context, intent, null)
                            }
                            noti = it
                        }
                    )

                }


                // Left Hand Mode
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    val sharedPreferences = LocalContext.current.getSharedPreferences("ForestOfLife", Context.MODE_PRIVATE)
                    var lefty by remember {
                        mutableStateOf(sharedPreferences.getBoolean("LeftHandMode", false))
                    }

                    val context = LocalContext.current
                    Text(
                        text = "Left Hand Mode",
                        style = textStyle
                    )

                    androidx.compose.material3.Switch(
                        modifier = Modifier.semantics { contentDescription = "Left Hand Mode" },
                        checked = lefty,
                        onCheckedChange = {
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("LeftHandMode", it)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                                editor.apply()
                            }
                            lefty = it
                        }
                    )

                }
                
            }


        }
    }
}
private fun mToast(context: Context){
    Toast.makeText(context, "Please turn off notification from settings", Toast.LENGTH_LONG).show()
}
@Preview
@Composable
fun ProfilePreview(){

}