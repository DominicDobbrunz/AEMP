package de.syntax.aemp.start

import de.syntax.aemp.R

sealed class BottomNavItem(val route: String, val label: String, val icon: Int) {
    data object Dental : BottomNavItem("dentals", "Geräte", R.drawable.icons8_sp_lmaschine_48)
    data object Favorites : BottomNavItem("favorites", "Favoriten", R.drawable.icons8_spritze_48)
    data object Settings : BottomNavItem("settings", "Einstellungen", R.drawable.icons8_resume_48)

    companion object {

        val items = listOf(Dental, Favorites, Settings)
    }
}