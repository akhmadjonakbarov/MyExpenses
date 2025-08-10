package uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model

import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.Repetition
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionGroup

data class DashboardState(
    val manageCreatingTransactionBottomSheet: Boolean = false,
    val transactionType: TransactionType = TransactionType.Expense,
    val receiverText: String = "",
    val noteText: String = "",
    val amountText: String = "",
    val selectedCategoryUi: CategoryUi = CategoryUi.ENTERTAINMENT,
    val selectedRepetition: Repetition = Repetition.Daily,
    val transactions: List<TransactionGroup> = emptyList(),
    val exportBottomSheet: Boolean = false,
)