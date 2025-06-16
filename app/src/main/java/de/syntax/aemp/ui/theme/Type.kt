package de.syntax.aemp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.syntax.aemp.R

val adlamdisplayFontFamily = FontFamily(
    Font(R.font.adlamdisplay_regular)
)

val baseLine = Typography()
// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = baseLine.displayLarge.copy(fontFamily = adlamdisplayFontFamily),
    displayMedium = baseLine.displayMedium.copy(fontFamily = adlamdisplayFontFamily),
    displaySmall = baseLine.displaySmall.copy(fontFamily = adlamdisplayFontFamily),
    headlineLarge = baseLine.headlineLarge.copy(fontFamily = adlamdisplayFontFamily),
    headlineMedium = baseLine.headlineMedium.copy(fontFamily = adlamdisplayFontFamily),
    headlineSmall = baseLine.headlineSmall.copy(fontFamily = adlamdisplayFontFamily),
    titleLarge = baseLine.titleLarge.copy(fontFamily = adlamdisplayFontFamily),
    titleMedium = baseLine.titleMedium.copy(fontFamily = adlamdisplayFontFamily),
    titleSmall = baseLine.titleSmall.copy(fontFamily = adlamdisplayFontFamily),
    bodyLarge = baseLine.bodyLarge.copy(fontFamily = adlamdisplayFontFamily),
    bodyMedium = baseLine.bodyMedium.copy(fontFamily = adlamdisplayFontFamily),
    bodySmall = baseLine.bodySmall.copy(fontFamily = adlamdisplayFontFamily),
    labelLarge = baseLine.labelLarge.copy(fontFamily = adlamdisplayFontFamily),
    labelMedium = baseLine.labelMedium.copy(fontFamily = adlamdisplayFontFamily),
    labelSmall = baseLine.labelSmall.copy(fontFamily = adlamdisplayFontFamily),
)



