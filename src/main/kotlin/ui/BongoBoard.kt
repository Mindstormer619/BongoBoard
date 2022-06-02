package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import ui.components.CircularButton
import ui.components.NumberInput
import ui.state.BoardState
import ui.state.rememberBoardState

@Composable
@Preview
fun BongoBoard(state: BoardState = rememberBoardState()) {
	BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
		val boardSpaceHeight = maxHeight - BoardState.OPTIONS_ROW_HEIGHT

		Column(modifier = Modifier.fillMaxSize()) {
			PadSpace(boardSpaceHeight, state)
			OptionsRow(state)
		}
	}
}

@Composable
fun OptionsRow(state: BoardState) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = Modifier.fillMaxWidth()
	) {
		val height = BoardState.OPTIONS_ROW_HEIGHT

		Row(verticalAlignment = Alignment.CenterVertically) {
			Spacer(modifier = Modifier.width(5.dp))
			CircularButton(
				onClick = { state.toggleEdit() },
				size = height,
				color = Color.Gray
			) {
				Text(if (state.isEditMode()) "💾" else "✏")
			}
		}

		if (state.isEditMode()) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				RowCountEditor(height, state)
				Spacer(modifier = Modifier.width(30.dp))
				ColumnCountEditor(height, state)
				Spacer(modifier = Modifier.width(5.dp))
			}
		}
	}
}

@Composable private fun RowCountEditor(height: Dp, state: BoardState) {
	Text("Rows:")
	Spacer(modifier = Modifier.width(5.dp))
	NumberInput(
		DpSize(100.dp, height),
		value = state.boardRows,
		onValueChange = { state.updateRows(it) },
		modifier = Modifier.testTag("rowEditor")
	)
}

@Composable private fun ColumnCountEditor(height: Dp, state: BoardState) {
	Text("Columns:")
	Spacer(modifier = Modifier.width(5.dp))
	NumberInput(
		DpSize(100.dp, height),
		value = state.boardColumns,
		onValueChange = state::updateCols,
		modifier = Modifier.testTag("columnEditor")
	)
}
