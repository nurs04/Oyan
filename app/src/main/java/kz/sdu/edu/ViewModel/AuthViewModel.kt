package kz.sdu.edu.ViewModel

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.sdu.edu.Util.Validator
import kz.sdu.edu.models.AuthResponse
import kz.sdu.edu.models.GenresResponse
import kz.sdu.edu.repository.AuthRepository

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState: StateFlow<AuthResponse?> = _authState.asStateFlow()

    fun register(
        username: String,
        email: String,
        password1: String,
        password2: String,
    ) {
        viewModelScope.launch {
        try {
            val response = repository.register(username, email, password1, password2)
            _authState.value = response
            response!!
        }catch (e : Exception){
            Log.e("REGISTER", "Ошибка при регистрации", e)
            }
        }
    }
    fun login(
        identifier: String,
        password: String,
    ) {
        viewModelScope.launch {
            try {
                val response = repository.login(identifier, password)
                _authState.value = response
            }catch (e : Exception){
                Log.e("LOGIN", "Ошибка входа", e)
            }
        }
    }

    fun hideKeyboard(view : View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
