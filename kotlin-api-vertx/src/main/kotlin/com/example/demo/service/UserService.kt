package com.example.demo.service

import com.example.demo.entity.UserEntity
import com.example.demo.exception.SaveUserException
import com.example.demo.exception.UserNotFoundException

class UserService(){//private val userRepository : UserRepository){

    fun findByLogin(login: String): UserEntity {
        try {
            return UserEntity("login", "firstname", "lastname")//userRepository.findByLogin(login)
        }catch (ex: Exception){
            throw UserNotFoundException(login)
        }
    }

    fun save(userEntity: UserEntity): UserEntity {
        try {
            return UserEntity("login", "firstname", "lastname") //userRepository.save(userEntity)
        }catch(ex: Exception){
            ex.printStackTrace()
            throw SaveUserException(userEntity.login)
        }
    }

}