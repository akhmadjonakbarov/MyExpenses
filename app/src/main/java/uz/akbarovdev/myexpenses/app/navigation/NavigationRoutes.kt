package uz.akbarovdev.myexpenses.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoutes {
    @Serializable
    data object Dashboard : NavigationRoutes

    @Serializable
    data object Settings : NavigationRoutes

    @Serializable
    data object Transactions : NavigationRoutes

    @Serializable
    data object Preference : NavigationRoutes
}