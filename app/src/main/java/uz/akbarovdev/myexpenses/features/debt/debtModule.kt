package uz.akbarovdev.myexpenses.features.debt

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import uz.akbarovdev.myexpenses.features.debt.data.repositories.DebtRepositoryImpl
import uz.akbarovdev.myexpenses.features.debt.domain.repositories.DebtRepository
import uz.akbarovdev.myexpenses.features.debt.presentation.view_model.DebtDetailViewModel
import uz.akbarovdev.myexpenses.features.debt.presentation.view_model.DebtListViewModel

val debtModule = module {
    singleOf(::DebtRepositoryImpl) bind DebtRepository::class
    viewModel { DebtListViewModel(get()) }
    viewModel { DebtDetailViewModel(get()) }
}
