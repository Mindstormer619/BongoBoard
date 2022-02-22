package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
			BoardSpace(boardSpaceHeight, state)
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

		CircularButton(
			onClick = {state.toggleEdit()},
			size = height,
			color = Color.Gray
		) {
			Text(if (state.isEditMode()) "💾" else "✏")
		}

		if (state.isEditMode()) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Text("Rows:")
				Spacer(modifier = Modifier.width(5.dp))
				NumberInput(DpSize(100.dp, height), value = state.boardRows, onValueChange = { state.updateRows(it) })
				Spacer(modifier = Modifier.width(30.dp))
				Text("Columns:")
				Spacer(modifier = Modifier.width(5.dp))
				NumberInput(DpSize(100.dp, height), value = state.boardColumns, onValueChange = state::updateCols)
			}
		}
	}
}
