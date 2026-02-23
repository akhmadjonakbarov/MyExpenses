package uz.akbarovdev.myexpenses.features.dashboard.domain.models

import androidx.compose.ui.graphics.Color
import uz.akbarovdev.myexpenses.R


enum class CategoryUi(
    val emoji: String,
    val label: String,
    val color: Color,
    val code: Int
) {
    ENTERTAINMENT(
        emoji = "💻",
        label = "Entertainment",
        code = R.string.entertainment,
        color = Color(0xFF4CAF50)
    ),
    CLOTHING(
        emoji = "👔",
        label = "Clothing & Accessories",
        code = R.string.clothes,
        color = Color(0xFF2196F3)
    ),
    EDUCATION(
        emoji = "🎓",
        label = "Education",
        code = R.string.education,
        color = Color(0xFFFF9800),
    ),
    FOOD(
        emoji = "🍕",
        label = "Food & Groceries",
        code = R.string.food,
        color = Color(0xFFE91E63)
    ),
    HEALTH(
        emoji = "❤️",
        label = "Health & Wellness",
        code = R.string.health,
        color = Color(0xFFF44336)
    ),
    DRINKS(
        emoji = "🥤",
        label = "Drinks",
        code = R.string.drinks,
        color = Color(0xFF9C27B0) // Example purple color
    )
}
