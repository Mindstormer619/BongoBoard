package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ui.state.BoardState
import ui.state.ButtonState

@Composable
fun BoardSpace(
	height: Dp,
	state: BoardState
) {
	BoxWithConstraints(
		modifier = Modifier
			.border(width = 1.dp, color = Color.Black)
			.fillMaxWidth()
			.height(height),
		contentAlignment = Alignment.Center
	) {
		val rowHeight = maxHeight / state.boardRows
		val rowModifier = Modifier.height(rowHeight).fillMaxWidth()
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
		if (state.buttonBeingEdited != null) {
			EditButtonDialog(state)
		}
	}
}

@Composable
fun EditButtonDialog(state: BoardState) {
	Dialog(onCloseRequest = { state.buttonBeingEdited = null }) {
		Column(
			modifier = Modifier.fillMaxWidth().fillMaxHeight(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceEvenly
		) {
			val buttonIndex = state.buttonBeingEdited ?: return@Column
			if (!state.buttons.containsKey(buttonIndex)) state.buttons[buttonIndex] = ButtonState("", null)
			val button = state.buttons.getValue(buttonIndex)
			Text(button.name)
			LaunchedEffect(button.name) {
				state.buttonNameBeingEdited = button.name
				state.mediaPathBeingEdited = button.media?.path ?: ""
			}

			TextField(
				value = state.buttonNameBeingEdited,
				onValueChange = { state.buttonNameBeingEdited = it },
				singleLine = true
			)
			TextField(
				value = state.mediaPathBeingEdited,
				onValueChange = { state.mediaPathBeingEdited = it },
				singleLine = true
			)
			Button(onClick = {
				state.buttons[buttonIndex] = ButtonState(state.buttonNameBeingEdited, state.mediaPathBeingEdited)
				state.buttonBeingEdited = null
			}) {
				Text("✅")
			}


			Button(
				onClick = {
					state.buttons -= buttonIndex
					state.buttonBeingEdited = null
				},
				colors = ButtonDefaults.buttonColors(Color.Red)
			) {
				Text("REMOVE")
			}
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
	Box(
		modifier = modifier
			.border(1.dp, Color.Black)
			.clickable(enabled = state.isEditMode()) {
				if ((row to col) in state.buttons) {
					state.buttons -= (row to col)
				} else {
					state.buttonBeingEdited = row to col
				}
			}
	) {
		if ((row to col) in state.buttons) {
			Button(
				modifier = Modifier.matchParentSize(),
				onClick = {
					if (state.isEditMode()) {
						state.buttonBeingEdited = row to col
					} else {
						state.buttons.getValue(row to col).media?.play()
					}
				}
			) {
				Text(state.buttons.getValue(row to col).name)
			}
		}
	}
}