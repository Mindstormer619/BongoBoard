package ui

import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import state.BoardImpl
import state.Pad
import ui.state.BoardUi
import ui.state.padPositionBeingEdited
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * TODO: Refactor these tests to actually use the dialog when
 * 	[#2107](https://github.com/JetBrains/compose-jb/issues/2107) gets fixed.
 */
class EditDialogTest {
	@get:Rule
	val compose = createComposeRule()

	private lateinit var boardState: BoardUi

	private lateinit var boardImpl: BoardImpl

	@Before
	fun setUp() {
		boardImpl = BoardImpl(
			pads = mutableMapOf(
				(1 to 1) to Pad("E", "D:\\Workspace\\Misc\\Soundboard\\e.wav", (1 to 1)),
				(1 to 2) to Pad("50 Ten Hull", "D:\\Workspace\\Misc\\Soundboard\\fiftyTenHull.mp3", (1 to 2)),
				(2 to 1) to Pad("Oof", "D:\\Workspace\\Misc\\Soundboard\\oof.wav", (2 to 1)),
				(2 to 2) to Pad("🎉", "D:\\Workspace\\Misc\\Soundboard\\tadaah.wav", (2 to 2))
			)
		)
		boardState = BoardUi(boardImpl)
		boardState.updateRowCount(3)
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

			println(onRoot().printToString())

			onAllNodesWithText("50 Ten Hull").assertCountEquals(2)
			onNodeWithText("fiftyTenHull.mp3", substring = true).assertExists()
		}
	}

	@Test
	@Ignore("https://github.com/JetBrains/compose-jb/issues/2107")
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

			val pad = boardImpl.pads.first().getValue(gridPosition)
			assertEquals("trombone", pad.name)
			assertTrue { pad.action.path.endsWith("sadTrombone.mp3") }
		}
	}
}