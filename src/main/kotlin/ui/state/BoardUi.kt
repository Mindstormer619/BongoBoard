package ui.state

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import state.Board
import state.BoardImpl
import state.GridPosition
import state.Pad

class BoardUi(private val board: BoardImpl): Board by board {
	val rows: Int
		@Composable get() = board.rows.collectAsState().value
	val cols: Int
		@Composable get() = board.cols.collectAsState().value
	val pads: Map<Pair<Int, Int>, Pad>
		@Composable get() = board.pads.collectAsState(board.padState).value

	private var mode: Mode by mutableStateOf(Mode.PLAY)

	@Composable fun hasPadAt(coordinates: GridPosition) = coordinates in pads

	fun isEditMode() = mode == Mode.EDIT
	fun toggleEdit() {
		mode =
			if (mode == Mode.PLAY) Mode.EDIT
			else Mode.PLAY
	}

	private enum class Mode {PLAY, EDIT}
}

/**
 * Set to null whenever the board state is not actively in edit mode.
 */
var padPositionBeingEdited: GridPosition? by mutableStateOf(null)

val OPTIONS_ROW_HEIGHT = 50.dp