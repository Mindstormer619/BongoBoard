package state

import javafx.scene.media.AudioClip
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.nio.file.Paths

@Serializable
class Pad(
	val name: String,
	val action: Audio, //TODO: Change this to a generic interface for other actions
	val coordinates: GridPosition
) {
	constructor(name: String, mediaPath: String, coordinates: GridPosition) : this(name, Audio(mediaPath), coordinates)

	fun activate() {
		action.activate()
	}
}

@Serializable
class Audio(
	val path: String
) {
	@Transient
	private val media = getAudio(path)

	fun activate() = media.play()
}

fun getAudio(path: String) = AudioClip(Paths.get(path).toUri().toString())
