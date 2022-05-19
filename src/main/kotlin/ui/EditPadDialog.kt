package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import ui.state.GridPosition
import ui.state.PadState

@Composable
fun EditPadDialog(state: BoardState) {
	Dialog(onCloseRequest = { state.padPositionBeingEdited = null }) {
		val padIndex = state.padPositionBeingEdited
		if (padIndex != null) {
			DialogBody(state, padIndex)
		}
	}
}

@Composable private fun DialogBody(state: BoardState, padIndex: GridPosition) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		val pad = state.getPadBeingEdited()

		Text(pad?.name ?: "")
		LaunchedEffect(pad) {
			state.buttonNameBeingEdited = pad?.name ?: ""
			state.mediaPathBeingEdited = pad?.media?.path ?: ""
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
			state.pads[padIndex] = PadState(state.buttonNameBeingEdited, state.mediaPathBeingEdited, padIndex)
			state.padPositionBeingEdited = null
		}) {
			Text("✅")
		}


		Button(
			onClick = {
				state.pads -= padIndex
				state.padPositionBeingEdited = null
			},
			colors = ButtonDefaults.buttonColors(Color.Red)
		) {
			Text("REMOVE")
		}
	}
}
