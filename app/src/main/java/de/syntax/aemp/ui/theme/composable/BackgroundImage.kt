package de.syntax.aemp.ui.theme.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import de.syntax.aemp.R

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.medi1),
        contentDescription = "Hintergrund",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.7f),
        contentScale = ContentScale.Crop

    )
}

@Preview(showBackground = true)
@Composable
fun BackGroundPreviw() {
    BackgroundImage()
}