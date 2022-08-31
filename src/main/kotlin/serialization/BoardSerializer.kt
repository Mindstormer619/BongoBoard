package serialization

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import state.Board
import state.Pad
import java.io.File

class BoardSerializer(
	private val board: Board,
	private val serializedFile: File,
	scope: CoroutineScope
) {
	init {
		board.rows.onEach { saveToFile() }.launchIn(scope)
		board.cols.onEach { saveToFile() }.launchIn(scope)
		board.pads.onEach { saveToFile() }.launchIn(scope)
	}

	@Suppress("BlockingMethodInNonBlockingContext")
	private suspend fun saveToFile() {
		withContext(Dispatchers.IO) {
			serializedFile.writeText(
				serializeToString(
					BoardState(
						board.rows.first(),
						board.cols.first(),
						board.pads.first().values.toList()
					)
				)
			)
		}
	}

	fun serializeToString(boardState: BoardState) = Json.encodeToString(boardState)
}

@Serializable
data class BoardState(
	val rows: Int,
	val cols: Int,
	val pads: List<Pad>
)