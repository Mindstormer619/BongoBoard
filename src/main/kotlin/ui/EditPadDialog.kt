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
import state.GridPosition
import state.Pad
import ui.state.BoardUi
import ui.state.padPositionBeingEdited

@Composable
fun EditPadDialog(state: BoardUi) {
	Dialog(onCloseRequest = { padPositionBeingEdited = null }) {
		val padIndex = padPositionBeingEdited
		if (padIndex != null) {
			DialogBody(state, padIndex)
		}
	}
}

@Composable
fun DialogBody(state: BoardUi, padIndex: GridPosition) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		val pad = state.pads[padIndex]
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
				state.addPad(Pad(buttonNameBeingEdited, mediaPathBeingEdited, padIndex))
				padPositionBeingEdited = null
			},
			colors = ButtonDefaults.buttonColors(Color.Green)
		) {
			Text("✅")
		}


		Button(
			onClick = {
				state.removePadAtPosition(padIndex)
				padPositionBeingEdited = null
			},
			colors = ButtonDefaults.buttonColors(Color.Red)
		) {
			Text("REMOVE")
		}
	}
}
