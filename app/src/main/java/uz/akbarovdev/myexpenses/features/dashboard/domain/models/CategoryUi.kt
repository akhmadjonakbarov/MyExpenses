package uz.akbarovdev.myexpenses.features.dashboard.domain.models

import androidx.compose.ui.graphics.Color
import uz.akbarovdev.myexpenses.R

enum class CategoryUi(
    val emoji: String,
    val label: String,
    val color: Color,
    val code: Int
) {
    ALL(
        emoji = "📊",
        label = "All Categories",
        code = R.string.all, // Ensure you have R.string.all defined in your strings.xml
        color = Color(0xFF9E9E9E) // Neutral Grey
    ),
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
        color = Color(0xFF9C27B0)
    ),
    // --- NEW CATEGORIES ADDED BELOW ---
    TRANSPORT(
        emoji = "🚗",
        label = "Transport & Fuel",
        code = R.string.transport,
        color = Color(0xFF00BCD4) // Cyan
    ),
    HOUSING(
        emoji = "🏠",
        label = "Rent & Housing",
        code = R.string.housing,
        color = Color(0xFF795548) // Brown
    ),
    UTILITIES(
        emoji = "⚡",
        label = "Utilities & Bills",
        code = R.string.utilities,
        color = Color(0xFFFFEB3B) // Yellow
    ),
    SHOPPING(
        emoji = "🛒",
        label = "Shopping",
        code = R.string.shopping,
        color = Color(0xFF673AB7) // Deep Purple
    ),
    TRAVEL(
        emoji = "✈️",
        label = "Travel & Trips",
        code = R.string.travel,
        color = Color(0xFF03A9F4) // Light Blue
    ),
    INVESTMENT(
        emoji = "📈",
        label = "Investments & Savings",
        code = R.string.investment,
        color = Color(0xFF009688) // Teal
    ),
    GIFTS(
        emoji = "🎁",
        label = "Gifts & Donations",
        code = R.string.gifts,
        color = Color(0xFFFF5722) // Deep Orange
    ),
    OTHERS(
        emoji = "✨",
        label = "Others / Misc",
        code = R.string.others,
        color = Color(0xFF607D8B) // Blue Grey
    )
}