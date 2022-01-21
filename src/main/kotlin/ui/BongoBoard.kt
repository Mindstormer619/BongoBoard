package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import ui.components.NumberInput

@Composable
@Preview
fun BongoBoard() {
	BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
		val optionsRowHeight = 50.dp
		val boardSpaceHeight = this.maxHeight - optionsRowHeight

		var boardRows by remember { mutableStateOf(5) }
		var boardColumns by remember { mutableStateOf(6) }
		Column(modifier = Modifier.fillMaxSize()) {
			BoardSpace(
				boardSpaceHeight,
				columns = boardColumns,
				rows = boardRows
			)

			OptionsRow(
				optionsRowHeight,
				boardRows,
				{
					boardRows = when {
						it > 6 -> 6
						it < 1 -> 1
						else -> it
					}
				},
				boardColumns,
				{
					boardColumns = when {
						it > 10 -> 10
						it < 1 -> 1
						else -> it
					}
				}
			)
		}
	}
}

@Composable
fun OptionsRow(
	height: Dp,
	rows: Int,
	updateRows: (Int) -> Unit,
	cols: Int,
	updateCols: (Int) -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.End,
		modifier = Modifier.fillMaxWidth()
	) {
		Text("Rows:")
		Spacer(modifier = Modifier.width(5.dp))
		NumberInput(DpSize(100.dp, height), value = rows, onValueChange = updateRows)
		Spacer(modifier = Modifier.width(30.dp))
		Text("Columns:")
		Spacer(modifier = Modifier.width(5.dp))
		NumberInput(DpSize(100.dp, height), value = cols, onValueChange = updateCols)
	}
}
