package com.ssm.mapper

import com.ssm.model.UserEntity
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 *
 * @author setruth
 * @date 2025/6/9
 * @time 23:10
 */
@Mapper
interface UserMapper {

    @Insert("INSERT INTO user(name,account,password) VALUES(#{name}, #{account}, #{password})")
    fun insertUser(user: UserEntity): Int

    @Select("SELECT * FROM user WHERE account = #{account}")
    fun getUserByAccount(account: String): UserEntity?

    @Select("SELECT * FROM user WHERE name = #{name}")
    fun getUserByName(name: String): UserEntity?

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    fun getUserById(userId: Int): UserEntity?
}