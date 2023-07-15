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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

            val sharedPreferences = LocalContext.current.getSharedPreferences("forestoflife", Context.MODE_PRIVATE)
            var selectedImageUri by remember {
                mutableStateOf<Uri?>(null)
            }

            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { uri -> selectedImageUri = uri }
            )

            if (sharedPreferences.contains("profile")){
                selectedImageUri = Uri.parse(sharedPreferences.getString("profile", null))
            }

            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            if (selectedImageUri != null){
                LocalContext.current.contentResolver.takePersistableUriPermission(
                    selectedImageUri!!, flag)
                val editor = sharedPreferences.edit()
                editor.putString("profile", selectedImageUri.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    editor.apply()
                }

            }

            // Photo, Name,
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                
                IconButton(
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },

                    modifier = Modifier
                        .size(150.dp)
                        .clip(shape = CircleShape)
                        .background(color = colorResource(id = R.color.card_green))

                ) {

                    if (selectedImageUri == null){
                        Icon(
                            painter = painterResource(id = R.drawable.person_48px),
                            contentDescription = "Placeholder"

                        )

                    } else {

                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )

                    }

                }

            }

            // Trees Grown
            Row() {

            }

            // Flowers Grown
            Row() {

            }

            // Total Focus
            Row() {

            }

        }
    }
}

@Preview
@Composable
fun ProfilePreview(){

}