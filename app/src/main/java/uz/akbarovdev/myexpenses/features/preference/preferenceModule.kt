package uz.akbarovdev.myexpenses.features.preference

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceViewModel


val preferenceModule = module {
    viewModel {
        PreferenceViewModel(get())
    }
}