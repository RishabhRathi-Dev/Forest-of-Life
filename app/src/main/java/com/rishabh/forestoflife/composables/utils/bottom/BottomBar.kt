package com.rishabh.forestoflife.composables.utils.bottom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rishabh.forestoflife.R
import com.rishabh.forestoflife.composables.utils.helpers.HexagonShape
import com.rishabh.forestoflife.composables.utils.helpers.drawCustomHexagonPath

@Composable
fun BottomBar(navController: NavHostController){
    //TODO: Create Bottom bar ref https://www.boltuix.com/2022/08/custom-bottom-navigation-bar.html
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Focus,
        BottomBarScreen.Add,
        BottomBarScreen.Island,
        BottomBarScreen.Profile
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val background =
        if (selected) ((colorResource(id = R.color.card_green))) else Color.Transparent

    var contentColor =
        if (selected) colorResource(id = R.color.app_bg) else Color.Black

    val addBg = colorResource(id = R.color.app_yellow)

    if (isSystemInDarkTheme()){
        contentColor =
            if (selected) colorResource(id = R.color.app_bg) else colorResource(id = R.color.dark_mode_icon)
    }

    if (screen.title == "CreateTask"){
        val myShape = HexagonShape()

        Box(modifier = Modifier
            .offset(y=(-5).dp)
            .padding(5.dp)
            .drawWithContent {
                drawContent()
                drawPath(
                    path = drawCustomHexagonPath(size),
                    color = addBg,
                    style = Stroke(
                        width = 5.dp.toPx(),
                        pathEffect = PathEffect.cornerPathEffect(25f)
                    )
                )
            }
            .wrapContentSize()
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })

        ){
            Image(
                painter = painterResource(id = R.drawable.add_48px),
                contentDescription = "My Hexagon Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .wrapContentSize()
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                        shape = myShape
                        clip = true
                    }
                    .background(color = addBg)
                    .padding(5.dp)
            )
        }

    }

    else {

        Box(
            modifier = Modifier
                .height(50.dp)
                .clip(CircleShape)
                .background(background)
                .clickable(onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                })
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                    contentDescription = "icon",
                    tint = contentColor,
                )
                /*
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }

             */
            }
        }
    }
}

@Preview
@Composable
fun BottomBarPreview(){
    //BottomBar()
}