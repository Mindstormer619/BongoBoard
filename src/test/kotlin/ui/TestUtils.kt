package ui

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import kotlinx.coroutines.runBlocking

fun ui(compose: ComposeContentTestRule, testBody: suspend ComposeContentTestRule.() -> Unit) {
	runBlocking { compose.testBody() }
}