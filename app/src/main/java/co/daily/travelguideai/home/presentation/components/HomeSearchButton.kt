package co.daily.travelguideai.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import co.daily.travelguideai.ui.theme.DarkGreen

@Composable
fun HomeSearchButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.background(color = DarkGreen, shape = CircleShape)
    ) {
        Icon(imageVector = icon, contentDescription = "search", tint = Color.White)
    }
}

@Preview
@Composable
fun HomeSearchButtonPreview() {
    HomeSearchButton(
        icon = Icons.Default.Search,
        onClick = {}
    )
}