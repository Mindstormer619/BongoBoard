package state


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import utils.MutableMapFlow

class Board(
	rows: Int = 0,
	cols: Int = 0,
	pads: MutableMap<GridPosition, Pad> = mutableMapOf()
) {
	private val _rows: MutableStateFlow<Int> = MutableStateFlow(rows)
	private val _cols: MutableStateFlow<Int> = MutableStateFlow(cols)
	private val _pads = MutableMapFlow(pads)

	val rows = _rows.asStateFlow()
	val cols = _cols.asStateFlow()
	val pads = _pads.flow

	fun updateRowCount(value: Int) {
		_rows.value = value
	}

	fun updateColCount(value: Int) {
		_cols.value = value
	}

	fun addPad(pad: Pad) {
		_pads[pad.coordinates] = pad
	}

	fun removePadAtPosition(position: GridPosition) {
		_pads -= position
	}
}
typealias GridPosition = Pair<Int, Int>
