package state

import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BoardStateTest {

	@Test
	fun `given a board, updating row count notifies new state`() {
		val rowCounts = listOf(2, 6, 3)

		runBlockingTest {
			val board = Board()
			launch {
				for (rowCount in rowCounts) {
					board.updateRowCount(rowCount)
					delay(50)
				}
			}
			launch {
				board.rows.collectIndexed { i, rowCount ->
					assertEquals(rowCounts[i], rowCount)
					if (i == rowCounts.lastIndex) terminateCollector()
				}
			}
		}
	}

	@Test
	fun `given a board, updating col count notifies new state`() {
		val colCounts = listOf(2, 6, 3)

		runBlockingTest {
			val board = Board()
			launch {
				for (colCount in colCounts) {
					board.updateColCount(colCount)
					delay(50)
				}
			}
			launch {
				board.cols.collectIndexed { i, colCount ->
					assertEquals(colCounts[i], colCount)
					if (i == colCounts.lastIndex) terminateCollector()
				}
			}
		}
	}

	@Test
	fun `given a board, adding a pad notifies new state`() {
		val fakeAudio = mockk<Audio>(relaxed = true)

		runBlockingTest {
			val board = Board()
			launch {
				board.addPad(1 to 1, PadState("Bloo", fakeAudio, 1 to 1))
			}
			launch {
				board.pads.collect {
					val pad = it.getValue(1 to 1)
					assertEquals("Bloo", pad.name)
					assertEquals(1 to 1, pad.coordinates)
					terminateCollector()
				}
			}
		}
	}

	@Test
	fun `given board with pad, removing pad notifies state change`() {
		val fakeAudio = mockk<Audio>(relaxed = true)

		runBlockingTest {
			val board = Board()
			board.addPad(1 to 1, PadState("Bloo", fakeAudio, 1 to 1))
			assertTrue("Before we remove it, board has the pad") {
				board.pads.first().containsKey(1 to 1)
			}

			launch {
				board.removePad(1 to 1)
			}
			launch {
				val pads = board.pads.first()
				assertEquals(0, pads.size)
			}
		}
	}

	private fun CoroutineScope.terminateCollector() {
		cancel()
	}
}