package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable fun BongoBoard() {
	BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
		val optionsRowHeight = 40.dp
		val boardSpaceHeight = this.maxHeight - optionsRowHeight
		Column(modifier = Modifier.fillMaxSize()) {
			BoardSpace(boardSpaceHeight)
			OptionsRow(optionsRowHeight)
		}
	}
}

@Composable fun OptionsRow(height: Dp) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.End,
		modifier = Modifier.fillMaxWidth().height(height)
	) {
		Button(
			onClick = {},
			modifier = Modifier.fillMaxHeight(),
			colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
		) {
			Text("Add")
		}

		Button(
			onClick = {},
			modifier = Modifier.fillMaxHeight(),
			colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
		) {
			Text("Remove")
		}
	}
}
