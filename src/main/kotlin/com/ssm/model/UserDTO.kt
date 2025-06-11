package com.ssm.model


data class UserLoginDTO(
    var account: String,
    var password: String
)
data class UserRegisterDTO(
    var name: String,
    var account: String,
    var password: String
)