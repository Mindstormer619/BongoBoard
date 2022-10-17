package state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import serialization.BoardSerializer
import ui.state.BoardUi

@Composable
fun rememberBoardState(serializedStateString: String? = null) = remember {
	if (serializedStateString == null) BoardUi(BoardImpl())
	else BoardUi(BoardSerializer.deserializeToBoard(serializedStateString))
}
