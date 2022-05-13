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

	val buttons: MutableMap<GridPosition, ButtonState> = mutableStateMapOf(
		(1 to 1) to ButtonState("E", "D:\\Workspace\\Misc\\Soundboard\\e.wav"),
		(1 to 2) to ButtonState("50 Ten Hull", "D:\\Workspace\\Misc\\Soundboard\\fiftyTenHull.mp3"),
		(2 to 1) to ButtonState("Oof", "D:\\Workspace\\Misc\\Soundboard\\oof.wav"),
		(2 to 2) to ButtonState("🎉", "D:\\Workspace\\Misc\\Soundboard\\tadaah.wav")
	)

	var buttonBeingEdited: GridPosition? by mutableStateOf(null)
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
}

data class ButtonState(
	val name: String,
	val media: Audio?
) {
	constructor(name: String, mediaPath: String): this(name, Audio(mediaPath))
}

class Audio(
	val path: String
) {
	val media = getAudio(path)
	fun play() = media.play()
}

private enum class BoardMode { PLAY, EDIT }

private typealias GridPosition = Pair<Int, Int>
