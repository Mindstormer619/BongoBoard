@file:OptIn(ExperimentalUnitApi::class)

package ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

@Composable
fun NumberInput(size: DpSize, value: Int = 0, onValueChange: (Int) -> Unit = {}, modifier: Modifier) {
	val heightMod = modifier.height(size.height)
	Row(
		modifier = heightMod,
		verticalAlignment = Alignment.CenterVertically
	) {
		val fontSize = TextUnit(size.height.value / 4, TextUnitType.Sp)
		val contentMod = Modifier
			.fillMaxHeight()
		NumberIncrementButton({ onValueChange(value - 1) }, size.height, isPlus = false)
		TextField(
			value = value.toString(),
			onValueChange = { onValueChange(it.toIntOrNull() ?: value) },
			modifier = contentMod.width(size.width),
			textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = fontSize),
			singleLine = true
		)
		NumberIncrementButton({ onValueChange(value + 1) }, size.height, isPlus = true)

	}
}

@Composable
private fun NumberIncrementButton(
	clickAction: () -> Unit,
	dimension: Dp,
	isPlus: Boolean = false
) {
	val fontSize = TextUnit(dimension.value / 4, TextUnitType.Sp)
	CircularButton(
		onClick = clickAction,
		size = dimension,
		color = if (isPlus) Color.Green else Color.Red
	) {
		Text(if (isPlus) "➕" else "➖", fontSize = fontSize)
	}
}