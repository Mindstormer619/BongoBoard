package serialization

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
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
		board.subscribeToChanges(scope, ::saveToFile)
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

	companion object {
		fun deserializeToBoard(jsonString: String): Board {
			val boardState = Json.decodeFromString<BoardState>(jsonString)
			return Board(
				rows = boardState.rows,
				cols = boardState.cols,
				pads = boardState.pads.associateByTo(mutableMapOf(), Pad::coordinates)
			)
		}
	}
}

@Serializable
data class BoardState(
	val rows: Int,
	val cols: Int,
	val pads: List<Pad>
)