package uz.akbarovdev.myexpenses.features.dashboard.domain.models

import androidx.compose.ui.graphics.Color

enum class CategoryUi(
    val emoji: String,
    val label: String,
    val color: Color
) {
    ENTERTAINMENT(
        emoji = "💻",
        label = "Entertainment",
        color = Color(0xFF4CAF50)
    ),
    CLOTHING(
        emoji = "👔",
        label = "Clothing & Accessories",
        color = Color(0xFF2196F3)
    ),
    EDUCATION(
        emoji = "🎓",
        label = "Education",
        color = Color(0xFFFF9800)
    ),
    FOOD(
        emoji = "🍕",
        label = "Food & Groceries",
        color = Color(0xFFE91E63)
    ),
    HEALTH(
        emoji = "❤️",
        label = "Health & Wellness",
        color = Color(0xFFF44336)
    ),
    DRINKS(
        emoji = "🥤",
        label = "Drinks",
        color = Color(0xFF9C27B0) // Example purple color
    )
}
