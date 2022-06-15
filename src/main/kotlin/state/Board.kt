package state


import MutableMapFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Board {
	private val _rows: MutableStateFlow<Int> = MutableStateFlow(0)
	private val _cols: MutableStateFlow<Int> = MutableStateFlow(0)
	private val _pads = MutableMapFlow(mutableMapOf<GridPosition, PadState>())

	val rows = _rows.asStateFlow()
	val cols = _cols.asStateFlow()
	val pads = _pads.flow

	fun updateRowCount(value: Int) {
		_rows.value = value
	}

	fun updateColCount(value: Int) {
		_cols.value = value
	}

	fun addPad(position: GridPosition, pad: PadState) {
		_pads[position] = pad
	}

	fun removePad(position: GridPosition) {
		_pads -= position
	}
}