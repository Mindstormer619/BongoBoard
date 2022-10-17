package serialization

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import state.Audio
import state.BoardImpl
import state.Pad
import java.io.File
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BoardSerializerTest {
	private lateinit var board: BoardImpl
	private lateinit var serializer: BoardSerializer
	private val scope = TestCoroutineScope()

	private val serializedFile = File("src/test/resources/out.json")
	private val mockAudio = mockk<Audio>(relaxed = true)

	@Before
	fun setUp() {
		every { mockAudio.path } returns "src/test/resources/succ.mp3"

		val pads = mutableMapOf(
			(1 to 1) to Pad("A", mockAudio, 1 to 1),
			(1 to 2) to Pad("B", mockAudio, 1 to 2)
		)
		board = BoardImpl(3, 4, pads)

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
				delay(100) // delay to let the file be written

				val boardState = Json.decodeFromString<BoardState>(serializedFile.readText())
				assertEquals(6, boardState.cols)
				assertEquals(5, boardState.rows)
			}
		}
	}

	@Test
	fun `given board state updated, pad state is saved`() {
		runBlocking {
			launch {
				board.addPad(Pad("Test Pad", mockAudio, 1 to 3))
				delay(100)

				val jsonText = serializedFile.readText()
				println(jsonText)
				val boardState = Json.decodeFromString<BoardState>(jsonText)
				assertEquals(3, boardState.pads.size)
				assertEquals(1 to 3, boardState.pads[2].coordinates)
			}
		}
	}

	@Test
	fun `when serializer is triggered, resulting string is as expected`() {
		runBlockingTest {
			val serializedText = serializer.serializeToString(
				BoardState(
					board.rows.first(),
					board.cols.first(),
					board.pads.first().values.toList()
				)
			)
			val boardState = Json.decodeFromString<BoardState>(serializedText)
			assertEquals(4, boardState.cols)
		}
	}

	@Test
	fun `when given a valid board string, we can deserialize it to the state of the board`() {
		runBlockingTest {
			val jsonString = File("src/test/resources/testJsonInput.json").readText()
			val board = BoardSerializer.deserializeToBoard(jsonString)
			assertEquals(5, board.rows.value)
			assertEquals(6, board.cols.value)
			assertEquals(3, board.pads.first().size)
		}
	}
}