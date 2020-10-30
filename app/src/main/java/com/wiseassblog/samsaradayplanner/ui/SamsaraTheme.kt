package com.wiseassblog.samsaradayplanner.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.wiseassblog.samsaradayplanner.R

public val primary = Color(0xff333333)
public val primaryDark = Color(0xff121212)
public val accent = Color(0xffBB86FC)

internal val darkBlue = Color(0xff1A237E)
internal val burntOrange = Color(0xffD84315)
internal val green = Color(0xff388E3C)
internal val red = Color(0xffB71C1C)
internal val lime = Color(0xff827717)
internal val lightBlue = Color(0xff0288D1)
internal val mauve = Color(0xffBA68C8)
internal val brown = Color(0xff795548)
internal val teal = Color(0xff00897B)

val samsaraColors = darkColors(
    primary = primary,
    primaryVariant = primaryDark,
    secondary = accent
)

private val RobotoCondensed = fontFamily(
    font(R.font.robotocondensed_regular),
    font(R.font.robotocondensed_light, FontWeight.Light),
    font(R.font.robotocondensed_bold, FontWeight.Bold)
)



@Composable
fun SamsaraTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    val typography = Typography(
        defaultFontFamily = RobotoCondensed,

        h4 = TextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 34.sp
        ),
        h6 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            fontFamily = RobotoCondensed,
            letterSpacing = 3.sp
        )
    )

    MaterialTheme(
        colors = samsaraColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}