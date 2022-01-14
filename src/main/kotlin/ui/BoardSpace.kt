package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BoardSpace(height: Dp) {
	BoxWithConstraints(
		modifier = Modifier
			.border(width = 1.dp, color = Color.Black)
			.fillMaxWidth()
			.height(height),
		contentAlignment = Alignment.Center
	) {
		val width = this.maxWidth

		val colCount = 6
		val rowCount = 5

		val borderModifier = Modifier.border(width = 1.dp, color = Color.Black)
		val rowHeight = height / rowCount
		val rowModifier = borderModifier.height(rowHeight).fillMaxWidth()
		val cellModifier = borderModifier.width(width / colCount).height(rowHeight)
		Column {
			for (row in 1..rowCount) {
				Row(modifier = rowModifier) {
					for (col in 1..colCount) {
						Button(modifier = cellModifier, onClick = {}) {
							Text("Example")
						}
					}
				}
			}
		}

	}
}