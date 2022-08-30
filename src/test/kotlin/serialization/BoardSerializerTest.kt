package serialization

import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import state.Audio
import state.Board
import state.Pad
import java.io.File
import kotlin.test.assertEquals

class BoardSerializerTest {
	lateinit var board: Board
	lateinit var serializer: BoardSerializer

	private val serializedFile = File("src/test/kotlin/resources/output.json")

	@Before
	fun setUp() {
		val mockAudio = mockk<Audio>(relaxed = true)
		val pads = mutableMapOf(
			(1 to 1) to Pad("A", mockAudio, 1 to 1),
			(1 to 2) to Pad("B", mockAudio, 1 to 2)
		)
		board = Board(3, 4, pads)

		serializer = BoardSerializer(board, serializedFile)
	}

	@After
	fun tearDown() {
		serializedFile.delete()
	}

	@Test
	fun `given a state change to the board, serializer runs and saves it`() {
		runBlocking {
			launch {
				serializer.initialize()
			}
			launch {
				board.updateRowCount(4)
				delay(500)
				assertEquals("", serializedFile.readText())
			}
		}
	}
}