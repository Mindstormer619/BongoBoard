package ui.state

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import javafx.scene.media.AudioClip
import java.nio.file.Paths

fun getAudio(path: String) = AudioClip(Paths.get(path).toUri().toString())

@Composable
fun rememberBoardState() = remember { BoardState() }

class BoardState {

	var boardRows: Int by mutableStateOf(2)
		private set
	var boardColumns: Int by mutableStateOf(2)
		private set
	private var mode: BoardMode by mutableStateOf(BoardMode.PLAY)

	val pads: MutableMap<GridPosition, PadState> = mutableStateMapOf(
		(1 to 1) to PadState("E", "D:\\Workspace\\Misc\\Soundboard\\e.wav", (1 to 1)),
		(1 to 2) to PadState("50 Ten Hull", "D:\\Workspace\\Misc\\Soundboard\\fiftyTenHull.mp3", (1 to 2)),
		(2 to 1) to PadState("Oof", "D:\\Workspace\\Misc\\Soundboard\\oof.wav", (2 to 1)),
		(2 to 2) to PadState("🎉", "D:\\Workspace\\Misc\\Soundboard\\tadaah.wav", (2 to 2))
	)

	var padPositionBeingEdited: GridPosition? by mutableStateOf(null)
	var buttonNameBeingEdited: String by mutableStateOf("")
	var mediaPathBeingEdited: String by mutableStateOf("")

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
	fun getPadBeingEdited(): PadState? {
		return this.pads[padPositionBeingEdited]
	}

	private enum class BoardMode { PLAY, EDIT }
}

class PadState(
	val name: String,
	val media: Audio,
	val coordinates: GridPosition
) {
	constructor(name: String, mediaPath: String, coordinates: GridPosition): this(name, Audio(mediaPath), coordinates)
}

class Audio(
	val path: String
) {
	private val media = getAudio(path)
	fun play() = media.play()
}



typealias GridPosition = Pair<Int, Int>
