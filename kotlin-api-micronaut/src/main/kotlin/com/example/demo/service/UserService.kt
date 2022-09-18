package com.example.demo.service

import com.example.demo.entity.UserEntity
import com.example.demo.exception.SaveUserException
import com.example.demo.exception.UserNotFoundException
import com.example.demo.repository.UserRepository
import io.micronaut.data.exceptions.EmptyResultException
//import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UserService(private val userRepository : UserRepository){

    fun findByLogin(login: String): UserEntity {
        try {
            return userRepository.findByLogin(login)
        }catch (ex: EmptyResultException){
            throw UserNotFoundException(login)
        }
    }

    fun save(userEntity: UserEntity): UserEntity {
        try {
            return userRepository.save(userEntity)
        }catch(ex: Exception){
            ex.printStackTrace()
            throw SaveUserException(userEntity.login)
        }
    }

}