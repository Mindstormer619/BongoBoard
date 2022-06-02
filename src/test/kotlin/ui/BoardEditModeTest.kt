package ui

import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ui.state.BoardState
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BoardEditModeTest {
	@get:Rule
	val compose = createComposeRule()

	private lateinit var boardState: BoardState

	@Before
	fun setUp() {
		boardState = BoardState()
		boardState.updateRows(3)
		boardState.toggleEdit() // set edit state initially
	}

	@Test
	fun `given board in edit mode, clicking the save button switches it to play mode`() {
		ui(compose) {
			setContent {
				BongoBoard(remember { boardState })
			}

			onNodeWithText("💾").performClick()
			awaitIdle()

			onNodeWithText("✏").assertExists()
		}
	}

	@Test
	fun `given board in edit mode, clicking + adds a row`() {
		ui(compose) {
			setContent {
				BongoBoard(remember { boardState })
			}
			assertEquals(3, boardState.boardRows)

			val childOfRowEditor = hasParent(hasTestTag("rowEditor"))
			onNode(childOfRowEditor.and(hasText("➕"))).performClick()
			awaitIdle()

			assertEquals(4, boardState.boardRows)
		}
	}

	@Test
	fun `given board with max rows in edit mode, clicking + does nothing`() {
		ui(compose) {
			boardState = BoardState(pads = mapOf(), boardRows = BoardState.MAX_ROWS)
			boardState.toggleEdit()
			setContent {
				BongoBoard(remember { boardState })
			}
			assertEquals(BoardState.MAX_ROWS, boardState.boardRows)

			val childOfRowEditor = hasParent(hasTestTag("rowEditor"))
			onNode(childOfRowEditor.and(hasText("➕"))).performClick()
			awaitIdle()

			assertEquals(BoardState.MAX_ROWS, boardState.boardRows)
		}
	}

	@Test
	fun `given board in edit mode, clicking blank space opens new pad editor`() {
		with(compose) {
			setContent {
				BongoBoard(remember { boardState })
			}

			onNodeWithTag("Cell:${3 to 1}").performClick()
			waitForIdle()

			assertNotNull(boardState.padPositionBeingEdited)
			onNode(isDialog()).assertExists()
		}
	}
}