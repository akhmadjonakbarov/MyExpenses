package uz.akbarovdev.myexpenses.core.extension

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import uz.akbarovdev.myexpenses.core.constants.PrefKeys
import java.util.Locale

private class LocaleContextWrapper(base: Context, locale: Locale) : ContextWrapper(base) {
    private val localeResources: Resources by lazy {
        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)
        base.createConfigurationContext(config).resources
    }

    override fun getResources(): Resources = localeResources
}

object AppLocale {
    private const val DEFAULT_LANG = "uz"

    private var currentCode by mutableStateOf(DEFAULT_LANG)

    val locale: Locale get() = Locale(currentCode)

    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PrefKeys.PREFS_NAME, Context.MODE_PRIVATE)
        currentCode = prefs.getString(PrefKeys.LANGUAGE, DEFAULT_LANG) ?: DEFAULT_LANG
    }

    fun switchTo(code: String, context: Context) {
        currentCode = code
        context.getSharedPreferences(PrefKeys.PREFS_NAME, Context.MODE_PRIVATE).edit {
            putString(PrefKeys.LANGUAGE, code)
        }
    }

    fun wrapContext(context: Context): Context {
        Locale.setDefault(locale)
        return LocaleContextWrapper(context, locale)
    }

    @Composable
    fun Wrapper(content: @Composable () -> Unit) {
        val context = LocalContext.current
        val wrapped = wrapContext(context)
        CompositionLocalProvider(LocalContext provides wrapped) {
            content()
        }
    }
}
