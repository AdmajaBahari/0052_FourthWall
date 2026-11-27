package com.example.fourthwall.repository

import com.example.fourthwall.data.model.*
import com.example.fourthwall.data.remote.ApiConfig
import com.example.fourthwall.data.remote.AuthApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AuthRepository {

    private val authApi = ApiConfig.retrofit.create(AuthApi::class.java)

    suspend fun register(nama: String, email: String, password: String): Result<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authApi.register(RegisterRequest(nama, email, password))
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Registrasi gagal: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authApi.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Login gagal: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun refreshToken(refreshToken: String): Result<RefreshTokenResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authApi.refreshToken(RefreshTokenRequest(refreshToken))
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Refresh token gagal: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getProfile(): Result<ProfileResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authApi.getProfile()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Gagal mengambil profil: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
