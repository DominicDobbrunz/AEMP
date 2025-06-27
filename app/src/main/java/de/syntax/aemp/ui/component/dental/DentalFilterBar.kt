package de.syntax.aemp.ui.component.dental

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun DentalFilterBar(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf(
        "Alle Geräte",
        "Bestecke",
        "Große-Autoclaven",
        "Autoclaven",
        "Kleine-Autoclaven",
        "Thermodesinfektion",
        "Siegelgeräte",
        "Sterielgutlagerung",
        "Dokumentation",
        "Pflege",
        "Wasser-Aufbereitung",
        "Routineprüfung",
        "Verbrauchsmaterial",
        "Röntgengeräte",
        "Sonstiges"
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            FilterChip(
                text = category,
                selected = isSelected,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterBarPreview() {
         DentalFilterBar(
             selectedCategory = "Alle Geräte"
         ) { }
}