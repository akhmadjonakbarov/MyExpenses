package uz.akbarovdev.myexpenses.features.settings

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceViewModel
import uz.akbarovdev.myexpenses.features.settings.view_model.SettingsViewModel


val settingsModule = module {
    viewModel {
        SettingsViewModel()
    }
}