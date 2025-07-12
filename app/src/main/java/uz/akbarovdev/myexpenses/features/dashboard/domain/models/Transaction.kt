package uz.akbarovdev.myexpenses.features.dashboard.domain.models

data class Transaction(
    val icon: String = "✅",
    val name: String = "",
    val price: Double = 0.0,
)