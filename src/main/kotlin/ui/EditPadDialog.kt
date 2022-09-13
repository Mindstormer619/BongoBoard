package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.window.Dialog
import state.BoardState
import state.GridPosition
import state.Pad

@Composable
fun EditPadDialog(state: BoardState) {
	Dialog(onCloseRequest = { state.padPositionBeingEdited = null }) {
		val padIndex = state.padPositionBeingEdited
		if (padIndex != null) {
			DialogBody(state, padIndex)
		}
	}
}

@Composable
fun DialogBody(state: BoardState, padIndex: GridPosition) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		val pad = state.getPadBeingEdited()
		var buttonNameBeingEdited by remember { mutableStateOf(pad?.name ?: "") }
		var mediaPathBeingEdited by remember { mutableStateOf(pad?.action?.path ?: "") }

		Text(pad?.name ?: "<NEW PAD>")
		TextField(
			value = buttonNameBeingEdited,
			onValueChange = { buttonNameBeingEdited = it },
			singleLine = true,
			modifier = Modifier.testTag("ButtonName")
		)
		TextField(
			value = mediaPathBeingEdited,
			onValueChange = { mediaPathBeingEdited = it },
			singleLine = true,
			modifier = Modifier.testTag("MediaPath")
		)
		Button(
			onClick = {
				state.upsertPad(Pad(buttonNameBeingEdited, mediaPathBeingEdited, padIndex))
				state.padPositionBeingEdited = null
			},
			colors = ButtonDefaults.buttonColors(Color.Green)
		) {
			Text("✅")
		}


		Button(
			onClick = {
				state.removePad(padIndex)
				state.padPositionBeingEdited = null
			},
			colors = ButtonDefaults.buttonColors(Color.Red)
		) {
			Text("REMOVE")
		}
	}
}
