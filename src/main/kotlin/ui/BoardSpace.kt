package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ui.components.blackBorder
import ui.components.setHeightWithMaxWidth
import ui.state.BoardState
import ui.state.GridPosition
import ui.state.PadState

@Composable
fun BoardSpace(
	height: Dp,
	state: BoardState
) {
	BoxWithConstraints(
		modifier = Modifier.blackBorder().setHeightWithMaxWidth(height),
		contentAlignment = Alignment.Center
	) {
		val rowHeight = maxHeight / state.boardRows
		val rowModifier = Modifier.setHeightWithMaxWidth(rowHeight)
		val cellModifier = Modifier.width(maxWidth / state.boardColumns).height(rowHeight)
		Column {
			for (row in 1..state.boardRows) {
				Row(modifier = rowModifier) {
					for (col in 1..state.boardColumns) {
						Cell(cellModifier, state, row, col)
					}
				}
			}
		}
		if (state.padBeingEdited != null) {
			EditPadDialog(state)
		}
	}
}

@Composable
fun Cell(
	modifier: Modifier,
	state: BoardState,
	row: Int,
	col: Int
) {
	val coordinates = row to col
	Box(
		modifier = modifier.blackBorder()
			.clickable(enabled = state.isEditMode()) { addNewPad(coordinates, state) }
	) {
		if (coordinates in state.pads) {
			Pad(state, coordinates)
		}
	}
}

@Composable
private fun BoxScope.Pad(state: BoardState, coordinates: GridPosition) {
	Button(
		modifier = Modifier.matchParentSize(),
		onClick = {
			if (state.isEditMode()) {
				state.padBeingEdited = coordinates to state.pads.getValue(coordinates)
			} else {
				state.pads.getValue(coordinates).media?.play()
			}
		}
	) {
		Text(state.pads.getValue(coordinates).name)
	}
}

private fun addNewPad(coordinates: GridPosition, state: BoardState) {
	state.padBeingEdited = createNewPadAt(coordinates, state)
}

private fun createNewPadAt(coordinates: GridPosition, state: BoardState): Pair<GridPosition, PadState> {
	val newPad = PadState("", null)
	state.pads[coordinates] = newPad
	return coordinates to newPad
}