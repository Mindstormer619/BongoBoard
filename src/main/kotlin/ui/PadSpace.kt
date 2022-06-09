package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import state.BoardState
import state.GridPosition
import ui.components.blackBorder
import ui.components.setHeightWithMaxWidth

@Composable
fun PadSpace(
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
		if (state.padPositionBeingEdited != null) {
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
			.testTag("Cell:$coordinates")
			.clickable(enabled = state.isEditMode()) { state.padPositionBeingEdited = coordinates }
	) {
		if (state.hasPadAt(coordinates)) {
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
				state.padPositionBeingEdited = coordinates
			} else {
				state.activatePad(coordinates)
			}
		}
	) {
		Text(state.pads.getValue(coordinates).name)
	}
}
