package kz.sdu.edu.ViewModel

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.sdu.edu.models.AuthResponse
import kz.sdu.edu.network.NetworkResult
import kz.sdu.edu.repository.AuthRepository
import kz.sdu.edu.repository.AuthRepositoryImpl

class AuthViewModel(
    val repository: AuthRepository
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
            when (val result = repository.register(username, email, password1, password2)) {
                is NetworkResult.Success -> {
                    val authResponse = result.data
                    _authState.value = authResponse
                    if (authResponse.status == "success") {
                        repository.setLoggedIn(true)
                    }
                }
                is NetworkResult.Error -> {
                    Log.e("REGISTER", "Registration failed: ${result.message}")
                }
            }
        }
    }
    fun login(
        identifier: String,
        password: String,
    ) {
        viewModelScope.launch {
            if (repository.getCsrfToken().isNullOrEmpty()) {
                when(val csrfResult = repository.fetchCsrfToken()) {
                    is NetworkResult.Success -> Unit
                    is NetworkResult.Error -> {
                        Log.e("LOGIN", "CSRF token fetch failed: ${csrfResult.message}")
                        return@launch
                    }
                }
            }
            when (val result = repository.login(identifier, password)) {
                is NetworkResult.Success -> {
                    val authResponse = result.data
                    _authState.value = authResponse
                    if (authResponse.status == "success") {
                        repository.setLoggedIn(true)
                        repository.setGenresSelected(authResponse.preferred_genres)
                    }
                }
                is NetworkResult.Error -> {
                    Log.e("LOGIN", "Login failed: ${result.message}")
                }
            }
        }
    }

    fun hideKeyboard(view : View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

}
