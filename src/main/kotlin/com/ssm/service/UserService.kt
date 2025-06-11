package com.ssm.service

import com.ssm.mapper.UserMapper
import com.ssm.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.security.MessageDigest

@Service
class UserService(private val userMapper: UserMapper) {

    fun register(userRegister: UserRegisterDTO): HttpRes<Boolean> {
        userMapper.getUserByAccount(userRegister.account)?.also {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseRes("账号已存在", false))
        }
        userMapper.getUserByName(userRegister.name)?.also {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseRes("用户名称已存在", false))
        }
        val encryptedPassword = md5(userRegister.password)

        return UserEntity(
            name = userRegister.name,
            account = userRegister.account,
            password = encryptedPassword
        ).let { newUser ->
            userMapper.insertUser(newUser)
            ResponseEntity.ok(BaseRes("注册成功", true))
        }
    }

    fun login(userLogin: UserLoginDTO): HttpRes<Int> {
        return userMapper.getUserByAccount(userLogin.account)?.let { user ->
            val encryptedPassword = md5(userLogin.password)
            if (user.password == encryptedPassword) {
                ResponseEntity.ok(BaseRes("登录成功", user.userId))
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseRes("密码错误", null))
            }
        } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseRes("用户不存在", null))
    }

    fun getUserById(userId: Int): HttpRes<UserSummaryVO> {
        return userMapper.getUserById(userId)?.let { user ->
            val userSummaryVO = UserSummaryVO(userId = user.userId!!, name = user.name, account = user.account)
            ResponseEntity.ok(BaseRes("查询成功", userSummaryVO))
        } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseRes("用户不存在"))
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString(separator = "") { "%02x".format(it) }
    }
}