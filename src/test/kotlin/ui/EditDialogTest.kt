package ui

import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import state.BoardState
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * TODO: Refactor these tests to actually use the dialog when
 * 	[#2107](https://github.com/JetBrains/compose-jb/issues/2107) gets fixed.
 */
class EditDialogTest {
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
	fun `given empty cell in edit mode, dialog displays new pad content`() {
		ui(compose) {
			setContent {
				val gridPosition = 3 to 1
				val state = remember { boardState.apply { padPositionBeingEdited = gridPosition } }
				DialogBody(state, gridPosition)
			}
			println(onRoot().printToString())
			onNodeWithText("<NEW PAD>").assertExists()
		}
	}

	@Test
	fun `given existing cell in edit mode, dialog displays cell editor`() {
		ui(compose) {
			setContent {
				val gridPosition = 1 to 2
				val state = remember { boardState.apply { padPositionBeingEdited = gridPosition } }
				DialogBody(state, gridPosition)
			}

			onAllNodesWithText("50 Ten Hull").assertCountEquals(2)
			onNodeWithText("fiftyTenHull.mp3", substring = true).assertExists()
		}
	}

	@Test @Ignore("https://github.com/JetBrains/compose-jb/issues/2107")
	fun `given new cell in edit, updating information and saving adds to state`() {
		ui(compose) {
			val gridPosition = 3 to 1
			setContent {

				val state = remember { boardState.apply { padPositionBeingEdited = gridPosition } }
				DialogBody(state, gridPosition)
			}

			onNodeWithTag("ButtonName").performTextInput("trombone")
			onNodeWithTag("MediaPath").performTextInput("D:\\Workspace\\Misc\\Soundboard\\sadTrombone.mp3")
			onNodeWithText("✅").performClick()
			awaitIdle()

			val pad = boardState.pads.getValue(gridPosition)
			assertEquals("trombone", pad.name)
			assertTrue { pad.media.path.endsWith("sadTrombone.mp3") }
		}
	}
}