package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BoardSpace(
	height: Dp,
	columns: Int = 6,
	rows: Int = 5
) {
	BoxWithConstraints(
		modifier = Modifier
			.border(width = 1.dp, color = Color.Black)
			.fillMaxWidth()
			.height(height),
		contentAlignment = Alignment.Center
	) {
		val width = this.maxWidth

		val rowHeight = height / rows
		val rowModifier = Modifier.height(rowHeight).fillMaxWidth()
		val borderModifier = Modifier.border(1.dp, Color.Black).padding(1.dp)
		val cellModifier = borderModifier.width(width / columns).height(rowHeight)
		Column {
			for (row in 1..rows) {
				key(row) {
					Row(modifier = rowModifier) {
						for (col in 1..columns) {
							key(col) {
								Button(modifier = cellModifier, onClick = {}) {
									Text("$row, $col")
								}
							}
						}
					}
				}
			}
		}

	}
}