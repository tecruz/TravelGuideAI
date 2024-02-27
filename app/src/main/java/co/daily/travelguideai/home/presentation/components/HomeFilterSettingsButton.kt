package co.daily.travelguideai.home.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.daily.travelguideai.ui.theme.DarkGreen

@Composable
fun HomeFilterSettingsButton(
    onClick: () -> Unit,
    icon: ImageVector?,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(ButtonDefaults.MinHeight, ButtonDefaults.MinHeight),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = "icon", tint = DarkGreen)
        }
    }
}

@Preview
@Composable
fun HomeFilterSettingsButtonPreview() {
    HomeFilterSettingsButton({}, Icons.Default.Add)
}