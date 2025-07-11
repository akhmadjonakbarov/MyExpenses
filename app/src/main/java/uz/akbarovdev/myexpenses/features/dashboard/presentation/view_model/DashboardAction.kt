package uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model

sealed interface DashboardAction {
    data class OpenCreateTransactionBottomSheet(
        val manageValue: Boolean
    ) : DashboardAction
}