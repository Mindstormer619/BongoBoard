package serialization

import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import state.Audio
import state.Board
import state.Pad
import java.io.File
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BoardSerializerTest {
	lateinit var board: Board
	lateinit var serializer: BoardSerializer
	lateinit var scope: TestCoroutineScope

	private val serializedFile = File("src/test/resources/out.json")

	@Before
	fun setUp() {
		val mockAudio = mockk<Audio>(relaxed = true)
		val pads = mutableMapOf(
			(1 to 1) to Pad("A", mockAudio, 1 to 1),
			(1 to 2) to Pad("B", mockAudio, 1 to 2)
		)
		board = Board(3, 4, pads)

		scope = TestCoroutineScope()

		serializer = BoardSerializer(board, serializedFile, scope)
	}

	@After
	fun tearDown() {
		serializedFile.delete()
	}

	@Test
	fun `given a state change to the board, serializer runs and saves it`() {
		runBlocking {
			launch {
				board.updateRowCount(5)
				board.updateColCount(6)
				delay(500) // delay to let the file be written

				val boardState = Json.decodeFromString<BoardState>(serializedFile.readText())
				assertEquals(6, boardState.cols)
				assertEquals(5, boardState.rows)
			}
		}
	}

	@Test
	fun `given board state updated, pad state is saved`() {
		TODO("Not yet implemented")
	}

	@Test
	fun `when serializer is triggered, resulting string is as expected`() {
		val serializedText = serializer.serializeToString()
		val boardState = Json.decodeFromString<BoardState>(serializedText)
		assertEquals(4, boardState.cols)
	}
}