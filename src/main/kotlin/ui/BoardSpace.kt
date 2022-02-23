package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.state.BoardState

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
				key(row) {
					Row(modifier = rowModifier) {
						for (col in 1..state.boardColumns) {
							key(col) {
								Box(
									modifier = cellModifier
										.border(1.dp, Color.Black)
										.clickable(enabled = state.isEditMode()) {
											if ((row to col) in state.buttons) {
												state.buttons -= (row to col)
											} else {
												state.buttons += (row to col)
											}
										}
								) {
									if ((row to col) in state.buttons) {
										Button(
											modifier = cellModifier,
											onClick = {
												if (state.isEditMode()) {
													state.buttons -= (row to col)
												} else {
													state.media.play()
												}
											}
										) {
											Text("E")
										}
									}
								}
							}
						}
					}
				}
			}
		}

	}
}