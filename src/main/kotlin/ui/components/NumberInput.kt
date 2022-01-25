@file:OptIn(ExperimentalUnitApi::class)

package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

@Composable
fun NumberInput(size: DpSize, value: Int = 0, onValueChange: (Int) -> Unit = {}) {
	val heightMod = Modifier.height(size.height)
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
	OutlinedButton(
		onClick = clickAction,
		modifier = Modifier.size(dimension),
		shape = CircleShape,
		colors = ButtonDefaults.outlinedButtonColors(backgroundColor = if (isPlus) Color.Green else Color.Red)
	) {
		Text(if (isPlus) "➕" else "➖", fontSize = fontSize)
	}
}