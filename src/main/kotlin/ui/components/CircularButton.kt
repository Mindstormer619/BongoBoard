package ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CircularButton(
	onClick : () -> Unit,
	size: Dp,
	color: Color,
	modifier: Modifier = Modifier,
	content: @Composable RowScope.() -> Unit
) {
	OutlinedButton(
		onClick = onClick,
		modifier = modifier.size(size),
		shape = CircleShape,
		colors = ButtonDefaults.outlinedButtonColors(backgroundColor = color)
	) {
		content()
	}
}