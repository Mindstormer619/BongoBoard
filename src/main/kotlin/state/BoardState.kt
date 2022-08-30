package state

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun rememberBoardState(pads: Map<GridPosition, Pad>? = null) = remember {
	if (pads == null) BoardState()
	else BoardState(pads)
}

class BoardState(
	pads: Map<GridPosition, Pad>,
	boardRows: Int = 2,
	boardColumns: Int = 2
) {

	constructor() : this(
		pads = mapOf(
			(1 to 1) to Pad("E", "D:\\Workspace\\Misc\\Soundboard\\e.wav", (1 to 1)),
			(1 to 2) to Pad("50 Ten Hull", "D:\\Workspace\\Misc\\Soundboard\\fiftyTenHull.mp3", (1 to 2)),
			(2 to 1) to Pad("Oof", "D:\\Workspace\\Misc\\Soundboard\\oof.wav", (2 to 1)),
			(2 to 2) to Pad("🎉", "D:\\Workspace\\Misc\\Soundboard\\tadaah.wav", (2 to 2))
		)
	)

	var boardRows: Int by mutableStateOf(boardRows)
		private set
	var boardColumns: Int by mutableStateOf(boardColumns)
		private set
	private var mode: BoardMode by mutableStateOf(BoardMode.PLAY)

	private val _pads = pads.map { it.key to it.value }.toMutableStateMap()
	val pads: Map<GridPosition, Pad> = _pads

	var padPositionBeingEdited: GridPosition? by mutableStateOf(null)

	companion object {
		const val MAX_ROWS = 6
		const val MAX_COLS = 10
		val OPTIONS_ROW_HEIGHT = 50.dp
	}

	fun updateRows(rows: Int) {
		boardRows = when {
			rows > MAX_ROWS -> MAX_ROWS
			rows < 1 -> 1
			else -> rows
		}
	}

	fun updateCols(cols: Int) {
		boardColumns = when {
			cols > MAX_COLS -> MAX_COLS
			cols < 1 -> 1
			else -> cols
		}
	}

	fun toggleEdit() {
		mode =
			if (mode == BoardMode.PLAY) BoardMode.EDIT
			else BoardMode.PLAY
	}

	fun isEditMode() = mode == BoardMode.EDIT

	fun getPadBeingEdited(): Pad? {
		return this.pads[padPositionBeingEdited]
	}

	fun upsertPad(pad: Pad) {
		_pads[pad.coordinates] = pad
	}

	fun removePad(padIndex: GridPosition) {
		_pads -= padIndex
	}

	fun hasPadAt(coordinates: GridPosition) = coordinates in pads
	fun activatePad(coordinates: GridPosition) = pads[coordinates]?.activate() ?: Unit

	private enum class BoardMode { PLAY, EDIT }
}

