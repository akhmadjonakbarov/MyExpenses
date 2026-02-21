package uz.akbarovdev.myexpenses.core.extension

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import uz.cyberbro.avangardmss.core.databases.SharedPreferencesDelegate
import java.util.Locale


fun Context.getLocalizedContext(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    return createConfigurationContext(config)
}

fun Context.sharedPreferences(name: String, defaultValue: String = "") =
    SharedPreferencesDelegate(this, name, defaultValue)

fun Context.wrapLocale(locale: Locale): ContextWrapper {
    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    return ContextWrapper(createConfigurationContext(config))
}

fun Context.getVersion(): String? {
    return this.packageManager.getPackageInfo(this.packageName, 0).versionName
}

fun Context.getVersionCode(): Int {
    return this.packageManager.getPackageInfo(this.packageName, 0).versionCode
}

