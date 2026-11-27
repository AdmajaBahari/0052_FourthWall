package com.example.fourthwall.data.model

// Request models
data class RegisterRequest(
    val nama: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RefreshTokenRequest(
    val refreshToken: String
)

// Response models
data class RegisterResponse(
    val id: Int,
    val nama: String,
    val email: String,
    val role: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserData
)

data class RefreshTokenResponse(
    val accessToken: String
)

data class UserData(
    val id: Int,
    val nama: String,
    val email: String,
    val role: String
)

data class ProfileResponse(
    val id: Int,
    val nama: String,
    val email: String,
    val role: String
)
