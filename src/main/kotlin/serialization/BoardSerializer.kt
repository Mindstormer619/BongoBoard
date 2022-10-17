package serialization

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import state.BoardImpl
import state.Pad
import java.io.File

class BoardSerializer(
	private val board: BoardImpl,
	private val serializedFile: File,
	scope: CoroutineScope
) {
	init {
		board.addSubscriber(scope, ::saveToFile)
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
		fun deserializeToBoard(jsonString: String) =
			Json.decodeFromString<BoardState>(jsonString).toBoard()
	}
}

@Serializable
data class BoardState(
	val rows: Int,
	val cols: Int,
	val pads: List<Pad>
) {
	fun toBoard() = BoardImpl(
		rows = rows,
		cols = cols,
		pads = pads.associateByTo(mutableMapOf(), Pad::coordinates)
	)
}