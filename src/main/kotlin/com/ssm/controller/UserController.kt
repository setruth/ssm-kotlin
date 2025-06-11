package com.ssm.controller

import com.ssm.model.*
import com.ssm.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/register")
    fun register(
        @RequestBody userRegisterDTO: UserRegisterDTO
    ): HttpRes<Boolean> = userService.register(userRegisterDTO)

    @PostMapping("/login")
    fun login(
        @RequestBody userLoginDTO: UserLoginDTO
    ): HttpRes<Int> = userService.login(userLoginDTO)

    @GetMapping("/{userId}")
    fun getUserById(
        @PathVariable userId: Int
    ): HttpRes<UserSummaryVO> = userService.getUserById(userId)
}
