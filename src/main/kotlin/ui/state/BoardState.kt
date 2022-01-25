package ui.state

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun rememberBoardState() = remember { BoardState() }

class BoardState {
	var boardRows: Int by mutableStateOf(5)
		private set
	var boardColumns: Int by mutableStateOf(6)
		private set

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
}