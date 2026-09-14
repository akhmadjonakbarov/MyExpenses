package uz.akbarovdev.myexpenses.features.dashboard

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import uz.akbarovdev.myexpenses.features.dashboard.data.repositories.BalanceRepositoryImpl
import uz.akbarovdev.myexpenses.features.dashboard.data.repositories.TransactionRepositoryImpl
import uz.akbarovdev.myexpenses.features.dashboard.data.sync.CloudSyncRepository
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.BalanceRepository
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.TransactionRepository
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardViewModel

val dashboardModule = module {
    singleOf(::TransactionRepositoryImpl) bind TransactionRepository::class
    singleOf(::BalanceRepositoryImpl) bind BalanceRepository::class
    single { CloudSyncRepository(get(), get(), get(), get()) }
    viewModel {
        DashboardViewModel(get(), get(), get(), get(), get())
    }

}