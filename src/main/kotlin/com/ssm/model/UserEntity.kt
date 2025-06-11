package com.ssm.model

data class UserEntity(
    var userId: Int? = null,
    var name: String,
    var account: String,
    var password: String
)