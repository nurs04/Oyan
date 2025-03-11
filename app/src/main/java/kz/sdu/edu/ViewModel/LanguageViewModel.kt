package kz.sdu.edu.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import java.util.Locale
import androidx.lifecycle.ViewModel

class LanguageViewModel: ViewModel() {
    private val _language = mutableStateOf(Locale.getDefault().language)
    val language : State<String> = _language

    fun setLanguage(locale: Locale){
        _language.value = locale.language
        Locale.setDefault(locale)
    }
}