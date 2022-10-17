package state


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import utils.MutableMapFlow

interface Board {
	fun updateRowCount(value: Int)
	fun updateColCount(value: Int)
	fun addPad(pad: Pad)
	fun removePadAtPosition(position: GridPosition)
	fun addSubscriber(scope: CoroutineScope, subscriber: suspend () -> Unit)
	fun activatePad(coordinates: GridPosition)
}

class BoardImpl(
	rows: Int = 2,
	cols: Int = 2,
	pads: MutableMap<GridPosition, Pad> = mutableMapOf()
): Board {
	private val _rows: MutableStateFlow<Int> = MutableStateFlow(rows)
	private val _cols: MutableStateFlow<Int> = MutableStateFlow(cols)
	private val _pads = MutableMapFlow(pads)

	val rows = _rows.asStateFlow()
	val cols = _cols.asStateFlow()
	val pads = _pads.flow
	val padState: Map<GridPosition, Pad> = pads

	override fun updateRowCount(value: Int) {
		_rows.value = minOf(value, MAX_ROWS)
	}

	override fun updateColCount(value: Int) {
		_cols.value = minOf(value, MAX_COLS)
	}

	override fun addPad(pad: Pad) {
		_pads[pad.coordinates] = pad
	}

	override fun removePadAtPosition(position: GridPosition) {
		_pads -= position
	}

	override fun addSubscriber(scope: CoroutineScope, subscriber: suspend () -> Unit) {
		rows.onEach { subscriber() }.launchIn(scope)
		cols.onEach { subscriber() }.launchIn(scope)
		pads.onEach { subscriber() }.launchIn(scope)
	}

	override fun activatePad(coordinates: GridPosition) {
		_pads[coordinates]?.activate() ?: Unit
	}

	companion object {
		const val MAX_ROWS = 6
		const val MAX_COLS = 6
	}
}
typealias GridPosition = Pair<Int, Int>
