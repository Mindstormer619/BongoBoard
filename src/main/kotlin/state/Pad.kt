package state

import javafx.scene.media.AudioClip
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.nio.file.Paths

@Serializable
class Pad(
	val name: String,
	val media: Audio,
	val coordinates: GridPosition
) {
	constructor(name: String, mediaPath: String, coordinates: GridPosition) : this(name, Audio(mediaPath), coordinates)

	fun activate() {
		media.play()
	}
}

@Serializable
class Audio(
	val path: String
) {
	@Transient
	private val media = getAudio(path)

	fun play() = media.play()
}

fun getAudio(path: String) = AudioClip(Paths.get(path).toUri().toString())
