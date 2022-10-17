package ui

import App
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToString
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import state.Audio
import state.BoardImpl
import state.Pad
import ui.state.BoardUi

class BoardInitializationTest {
	@get:Rule
	val compose = createComposeRule()

	@Test
	fun `given initial state of board, loading the board shows init configuration`() {
		ui(compose) {
			setContent {
				App()
			}

			onNodeWithText("✏").assertExists() // Button to open edit mode
			onNodeWithText("💾").assertDoesNotExist() // Button to save edits
		}
	}

	@Test
	fun `given board in play mode, clicking on edit button switches to edit mode`() {
		ui(compose) {
			setContent { App() }

			// Click on the Edit button
			onNodeWithText("✏").performClick()
			awaitIdle()

			onNodeWithText("💾").assertExists()
			onNodeWithText("Rows:").assertExists()
		}
	}

	@Test
	fun `given board in play mode, clicking a pad activates it`() {
		ui(compose) {
			val gridPosition = 1 to 1
			val mockPad = spyk(Pad("E", mockk<Audio>(), gridPosition))
			every { mockPad.activate() } just runs

			setContent {
				BongoBoard(
					BoardUi(BoardImpl(pads = mutableMapOf(gridPosition to mockPad)))
				)
			}

			println(onRoot().printToString())

			onNodeWithText("E").performClick()
			awaitIdle()

			verify(exactly = 1) { mockPad.activate() }
		}
	}
}
