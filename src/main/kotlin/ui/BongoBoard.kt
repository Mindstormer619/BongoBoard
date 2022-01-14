package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BongoBoard() {
	BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
		val optionsRowHeight = 40.dp
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
				{ boardRows++ },
				{ boardColumns++ },
				{
					boardRows--
					if (boardRows < 1) boardRows = 1
				},
				{
					boardColumns--
					if (boardColumns < 1) boardColumns = 1
				}
			)
		}
	}
}

@Composable
fun OptionsRow(
	height: Dp,
	addRow: () -> Unit,
	addColumn: () -> Unit,
	delRow: () -> Unit,
	delColumn: () -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.End,
		modifier = Modifier.fillMaxWidth()
	) {
		val buttonModifier = Modifier
			.height(height)
			.padding(5.dp)
		Button(
			onClick = addRow,
			modifier = buttonModifier,
			colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
		) {
			Text("Add Row")
		}
		Button(
			onClick = addColumn,
			modifier = buttonModifier,
			colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
		) {
			Text("Add Column")
		}

		Button(
			onClick = delRow,
			modifier = buttonModifier,
			colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
		) {
			Text("Remove Row")
		}
		Button(
			onClick = delColumn,
			modifier = buttonModifier,
			colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
		) {
			Text("Remove Column")
		}
	}
}
