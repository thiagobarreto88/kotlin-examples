package com.example.demo.controller

import com.example.demo.entity.UserEntity
import com.example.demo.exception.UserNotFoundException
import com.example.demo.model.CreateUserRequest
import com.example.demo.service.UserService

class Controller (private val userService: UserService ) {

    fun create(request : CreateUserRequest): String {
        userService.save(UserEntity(login = request.login, firstname = request.firstname, lastname = request.lastname, description = request.description))
        return "OK"
    }

    fun get(login: String): UserEntity? {
        return userService.findByLogin(login)
    }

}
