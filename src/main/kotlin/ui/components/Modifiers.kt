package ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.blackBorder() = this.border(width = 1.dp, color = Color.Black)

fun Modifier.setHeightWithMaxWidth(height: Dp) = this.fillMaxWidth().height(height)