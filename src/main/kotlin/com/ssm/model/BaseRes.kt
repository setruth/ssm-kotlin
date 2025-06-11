package com.ssm.model

import org.springframework.http.ResponseEntity
//ResponseEntity太长了，写个别名吧
typealias HttpRes<T> = ResponseEntity<BaseRes<T>>

data class BaseRes<T>(
    val message: String = "",
    val data: T? = null,
)