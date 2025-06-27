package de.syntax.aemp.ui.component.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import de.syntax.aemp.R


@Composable
internal fun Launch() {
    Box {
        Image(
            painter = painterResource(R.drawable.medi),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchPreviw() {
    Launch()
}