package com.example.fourthwall.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fourthwall.data.model.LoginResponse
import com.example.fourthwall.data.model.RegisterResponse
import com.example.fourthwall.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val loginResponse: LoginResponse? = null,
    val registerResponse: RegisterResponse? = null
)

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Email dan password tidak boleh kosong"
            )
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.onSuccess { response ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    loginResponse = response
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = exception.message ?: "Login gagal"
                )
            }
        }
    }

    fun register(nama: String, email: String, password: String) {
        if (nama.isBlank() || email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Nama, email, dan password tidak boleh kosong"
            )
            return
        }

        if (password.length < 6) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Password minimal 6 karakter"
            )
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = authRepository.register(nama, email, password)
            result.onSuccess { response ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    registerResponse = response
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = exception.message ?: "Registrasi gagal"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetState() {
        _uiState.value = AuthUiState()
    }
}
