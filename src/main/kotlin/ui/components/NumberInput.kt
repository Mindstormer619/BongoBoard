package ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType


@OptIn(ExperimentalUnitApi::class)
@Composable
fun NumberInput(size: DpSize, value: Int = 0, onValueChange: (Int) -> Unit = {}) {
	val heightMod = Modifier.height(size.height)
	Row(
		modifier = heightMod,
		verticalAlignment = Alignment.CenterVertically
	) {
		val fontSize = TextUnit(size.height.value / 4, TextUnitType.Sp)
		val contentMod = Modifier
			.align(Alignment.CenterVertically)
			.fillMaxHeight()
		OutlinedButton(
			onClick = { onValueChange(value - 1) },
			modifier = contentMod.width(size.height),
			shape = CircleShape,
			colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Red)
		) {
			Text("➖", fontSize = fontSize)
		}
		TextField(
			value = value.toString(),
			onValueChange = { onValueChange(it.toIntOrNull() ?: value) },
			modifier = contentMod.width(size.width),
			textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = fontSize),
			singleLine = true
		)
		OutlinedButton(
			onClick = { onValueChange(value + 1) },
			modifier = contentMod.width(size.height),
			shape = CircleShape,
			colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Green)
		) {
			Text("➕", fontSize = fontSize)
		}
	}

}