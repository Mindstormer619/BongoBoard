package ui

import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import state.BoardImpl
import ui.state.BoardUi
import kotlin.test.assertEquals

class BoardEditModeTest {
	@get:Rule
	val compose = createComposeRule()

	private lateinit var boardUiState: BoardUi
	private lateinit var boardImpl: BoardImpl

	@Before
	fun setUp() {
		boardImpl = BoardImpl()
		boardUiState = BoardUi(boardImpl)
		boardUiState.updateRowCount(3)
		boardUiState.toggleEdit() // set edit state initially
	}

	@Test
	fun `given board in edit mode, clicking the save button switches it to play mode`() {
		ui(compose) {
			setContent {
				BongoBoard(remember { boardUiState })
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
				BongoBoard(remember { boardUiState })
			}
			assertEquals(3, boardImpl.rows.value)

			val childOfRowEditor = hasParent(hasTestTag("rowEditor"))
			onNode(childOfRowEditor.and(hasText("➕"))).performClick()
			awaitIdle()

			assertEquals(4, boardImpl.rows.value)
		}
	}

	@Test
	fun `given board with max rows in edit mode, clicking + does nothing`() {
		ui(compose) {
			// given
			boardImpl = BoardImpl(rows = BoardImpl.MAX_ROWS)
			boardUiState = BoardUi(boardImpl)

			boardUiState.toggleEdit()
			setContent {
				BongoBoard(remember { boardUiState })
			}
			assertEquals(BoardImpl.MAX_ROWS, boardImpl.rows.value)

			// when
			val childOfRowEditor = hasParent(hasTestTag("rowEditor"))
			onNode(childOfRowEditor.and(hasText("➕"))).performClick()
			awaitIdle()

			// then
			assertEquals(BoardImpl.MAX_ROWS, boardImpl.rows.value)
		}
	}

	@Test
	fun `given board with max cols in edit mode, clicking + does nothing`() {
		ui(compose) {
			// given
			boardImpl = BoardImpl(cols = BoardImpl.MAX_COLS)
			boardUiState = BoardUi(boardImpl)

			boardUiState.toggleEdit()
			setContent {
				BongoBoard(remember { boardUiState })
			}
			assertEquals(BoardImpl.MAX_COLS, boardImpl.cols.value)

			// when
			val colEditor = hasParent(hasTestTag("columnEditor"))
			onNode(colEditor.and(hasText("➕"))).performClick()
			awaitIdle()

			// then
			assertEquals(BoardImpl.MAX_COLS, boardImpl.cols.value)
		}
	}

	@Test @Ignore("https://github.com/JetBrains/compose-jb/issues/2107")
	fun `given board in edit mode, clicking blank space opens new pad editor`() {
		ui(compose) {
			setContent {
				BongoBoard(remember { boardUiState })
			}

			onNodeWithTag("Cell:${3 to 1}").performClick()
			awaitIdle()

			println(onRoot().printToString())
			onNode(isDialog(), useUnmergedTree = true).assertExists()
		}
	}
}