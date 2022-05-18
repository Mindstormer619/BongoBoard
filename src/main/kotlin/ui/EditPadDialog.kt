package ui

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
import androidx.compose.ui.window.Dialog
import ui.state.BoardState
import ui.state.PadState

@Composable
fun EditPadDialog(state: BoardState) {
	Dialog(onCloseRequest = { state.padBeingEdited = null }) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceEvenly
		) {
			val padIndex = state.padBeingEdited?.first ?: return@Column
			val pad = state.padBeingEdited?.second ?: return@Column

			Text(pad.name)
			LaunchedEffect(pad.name) {
				state.buttonNameBeingEdited = pad.name
				state.mediaPathBeingEdited = pad.media?.path ?: ""
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
				state.pads[padIndex] = PadState(state.buttonNameBeingEdited, state.mediaPathBeingEdited)
				state.padBeingEdited = null
			}) {
				Text("✅")
			}


			Button(
				onClick = {
					state.pads -= padIndex
					state.padBeingEdited = null
				},
				colors = ButtonDefaults.buttonColors(Color.Red)
			) {
				Text("REMOVE")
			}
		}
	}
}
