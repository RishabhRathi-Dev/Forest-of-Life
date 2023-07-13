package com.rishabh.forestoflife.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.headers.MainHeader

@Composable
fun Onboarding1(navHostController: NavHostController){
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
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            Text(
                text = "Forest\nof\nLife",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.itim)),
                    fontWeight = FontWeight(400),
                ),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            )

            Text(
                text = "Grow your own little forest by doing and focusing on your tasks.",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.itim)),
                    fontWeight = FontWeight(400),
                    letterSpacing = 0.35.sp,
                ),
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 16.dp, end = 16.dp)
            )

            Row(
                modifier = Modifier
                    .padding(top=10.dp, bottom=10.dp, start = 16.dp, end = 16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        //Toast.makeText(mContext, "This is a Circular Button with a + Icon", Toast.LENGTH_LONG).show()
                        navHostController.navigate("Onboarding2")
                              },
                    shape = CircleShape,
                    modifier= Modifier.size(60.dp),
                    border= BorderStroke(0.dp, color = colorResource(id = R.color.app_bg)),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor =  Color.Gray),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.arrow_forward_ios_48px) ,
                        contentDescription = "content description",
                        tint=Color.Black,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }

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
                        .offset(y = 5.dp)
                )

            }



        }
    }
}

@Preview
@Composable
fun Onboarding1Preview(){
    //Onboarding1()
}