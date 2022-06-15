import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MutableMapFlow<K, V>(
	private val map: MutableMap<K, V>
) : MutableMap<K, V> by map {
	private val _flow = MutableSharedFlow<Map<K, V>>(
		replay = 1,
		onBufferOverflow = BufferOverflow.DROP_OLDEST
	)
	val flow = _flow.asSharedFlow()

	override fun put(key: K, value: V): V? {
		val result = map.put(key, value)
		_flow.tryEmit(map)
		return result
	}

	override fun putAll(from: Map<out K, V>) {
		map.putAll(from)
		_flow.tryEmit(map)
	}

	override fun remove(key: K): V? {
		val result = map.remove(key)
		_flow.tryEmit(map)
		return result
	}
}