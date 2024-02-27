package co.daily.travelguideai.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.daily.travelguideai.home.domain.model.HomeFilterSettings
import co.daily.travelguideai.home.presentation.HomeFilterDialogAction

@Composable
fun HomeFilterDialog(
    onDismiss: () -> Unit,
    filterSettings: HomeFilterSettings,
    onAction: (HomeFilterDialogAction) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = onDismiss,
        modifier = modifier.fillMaxWidth(),
        containerColor = Color.White,
        confirmButton = {
            Button(
                onClick = { onAction(HomeFilterDialogAction.OnApplyClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .defaultMinSize(minHeight = 53.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Aplicar")
            }
        }, text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Pessoas")
                    HomeFilterIncrement(
                        number = filterSettings.people,
                        onMinus = { onAction(HomeFilterDialogAction.OnPeopleMinus) },
                        onPlus = { onAction(HomeFilterDialogAction.OnPeoplePlus) }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Restaurantes")
                    HomeFilterCheckbox(
                        onClick = { onAction(HomeFilterDialogAction.OnRestaurantClick) },
                        isChecked = filterSettings.restaurant
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Museus")
                    HomeFilterCheckbox(
                        onClick = { onAction(HomeFilterDialogAction.OnMuseumClick) },
                        isChecked = filterSettings.museums
                    )
                }
            }
        }, shape = RoundedCornerShape(30.dp)
    )
}

@Preview
@Composable
fun HomeFilterDialogPreview() {
    HomeFilterDialog({}, filterSettings = HomeFilterSettings(), {})
}