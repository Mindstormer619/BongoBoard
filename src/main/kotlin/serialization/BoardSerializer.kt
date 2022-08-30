package serialization

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import state.Board
import java.io.File

class BoardSerializer(
	private val board: Board,
	private val serializedFile: File,
	scope: CoroutineScope
) {
	init {
		board.rows.onEach { saveToFile() }.launchIn(scope)
		board.cols.onEach { saveToFile() }.launchIn(scope)
	}

	@Suppress("BlockingMethodInNonBlockingContext")
	private suspend fun saveToFile() {
		withContext(Dispatchers.IO) {
			serializedFile.writeText(serializeToString())
		}
	}

	fun serializeToString(): String {
		val boardState = BoardState(
			board.rows.value,
			board.cols.value
		)
		return Json.encodeToString(boardState)
	}
}

@Serializable
data class BoardState(
	val rows: Int,
	val cols: Int
)