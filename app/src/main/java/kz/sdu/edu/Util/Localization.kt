package kz.sdu.edu.Util

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

object Localization {
    var currentLocale: MutableState<Locale> = mutableStateOf(Locale.getDefault())

    fun updateLanguage(context: Context, locale: Locale){
        currentLocale.value = locale
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}