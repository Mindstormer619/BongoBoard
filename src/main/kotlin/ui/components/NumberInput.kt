package ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

@Preview
@Composable
fun NumberInputPreview() {
	MaterialTheme {
		NumberInput(DpSize(100.dp, 50.dp))
	}
}


@OptIn(ExperimentalUnitApi::class)
@Composable
fun NumberInput(size: DpSize) {
	val heightMod = Modifier.height(size.height)
	Row(
		modifier = heightMod,
		verticalAlignment = Alignment.CenterVertically
	) {
		val fontSize = TextUnit(size.height.value / 4, TextUnitType.Sp)
		val contentMod = Modifier
			.align(Alignment.CenterVertically)
			.fillMaxHeight()
		OutlinedButton(onClick = {}, modifier = contentMod.width(size.height), shape = CircleShape) {
			Text("➖", fontSize = fontSize)
		}
		TextField(
			value = "0",
			onValueChange = {},
			modifier = contentMod.width(size.width),
			textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = fontSize),
			singleLine = true
		)
		OutlinedButton(onClick = {}, modifier = contentMod.width(size.height), shape = CircleShape) {
			Text("➕", fontSize = fontSize)
		}
	}

}