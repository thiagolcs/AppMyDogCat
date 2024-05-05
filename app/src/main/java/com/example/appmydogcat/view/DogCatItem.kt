package com.example.appmydogcat.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage

@Composable
fun DogCatItem (
    item: String
) {

    val rowColor = if (isSystemInDarkTheme()) Color.White else Color.Gray

    val icon = if (isSystemInDarkTheme()) Color.White else Color.Black

    val isClicked = remember { mutableStateOf(false) }

    val iconColor = if (isClicked.value) Color.Red else icon


    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
    ) {
        val (
            favoriteIcon,
            containerImg,
            rowItem
        ) = createRefs()

        Card(
            modifier = Modifier
                .constrainAs(containerImg) {
                    top.linkTo(parent.top, 0.dp)
                    start.linkTo(parent.start, 10.dp)
                }
                .size(130.dp)
                .padding(20.dp)
                .clipToBounds(),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = ShapeDefaults.Medium,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )

        ) {
            AsyncImage(
                model = item,
                contentDescription = "Photo cat",
                modifier = Modifier
                    .fillMaxSize(),
                placeholder = ColorPainter(Color.Gray),
                contentScale = ContentScale.Crop
            )

        }

        Icon(
            imageVector = Icons.Rounded.FavoriteBorder,
            contentDescription = "Favorite Icon",
            modifier = Modifier
                .clickable {
                    isClicked.value = !isClicked.value
                }
                .padding(16.dp)
                .constrainAs(favoriteIcon) {
                    top.linkTo(parent.top, 40.dp)
                    end.linkTo(parent.end, 40.dp)
                }
                .size(28.dp),
            tint = iconColor
        )

        Row(
            modifier = Modifier
                .width(350.dp)
                .height(1.dp)
                .background(rowColor, shape = RoundedCornerShape(8.dp))
                .constrainAs(rowItem) {
                    top.linkTo(containerImg.bottom, 30.dp)
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                }
                .clip(RoundedCornerShape(30.dp))
        ) {

        }

    }
}



