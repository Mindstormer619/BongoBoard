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
import state.GridPosition
import ui.components.blackBorder
import ui.components.setHeightWithMaxWidth
import ui.state.BoardUi
import ui.state.padPositionBeingEdited

@Composable
fun PadSpace(
	height: Dp,
	state: BoardUi
) {
	BoxWithConstraints(
		modifier = Modifier.blackBorder().setHeightWithMaxWidth(height),
		contentAlignment = Alignment.Center
	) {
		val rowHeight = maxHeight / state.rows
		val rowModifier = Modifier.setHeightWithMaxWidth(rowHeight)
		val cellModifier = Modifier.width(maxWidth / state.cols).height(rowHeight)
		Column {
			for (row in 1..state.rows) {
				Row(modifier = rowModifier) {
					for (col in 1..state.cols) {
						Cell(cellModifier, state, row, col)
					}
				}
			}
		}
		if (padPositionBeingEdited != null) {
			EditPadDialog(state)
		}
	}
}

@Composable
fun Cell(
	modifier: Modifier,
	state: BoardUi,
	row: Int,
	col: Int
) {
	val coordinates = row to col
	Box(
		modifier = modifier.blackBorder()
			.testTag("Cell:$coordinates")
			.clickable(enabled = state.isEditMode()) { padPositionBeingEdited = coordinates }
	) {
		if (state.hasPadAt(coordinates)) {
			Pad(state, coordinates)
		}
	}
}

@Composable
private fun BoxScope.Pad(state: BoardUi, coordinates: GridPosition) {
	Button(
		modifier = Modifier.matchParentSize(),
		onClick = {
			if (state.isEditMode()) {
				padPositionBeingEdited = coordinates
			} else {
				state.activatePad(coordinates)
			}
		}
	) {
		Text(state.pads.getValue(coordinates).name)
	}
}
