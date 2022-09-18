package com.example.demo.controller

import com.example.demo.entity.UserEntity
import com.example.demo.exception.UserNotFoundException
import com.example.demo.model.CreateUserRequest
import com.example.demo.service.UserService
import io.micronaut.data.exceptions.EmptyResultException
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Controller
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@Controller
class Controller (private val userService: UserService ) {

    @Post("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun create(@Body request : CreateUserRequest): String {
        userService.save(UserEntity(login = request.login, firstname = request.firstname, lastname = request.lastname, description = request.description))
        return "OK"
    }

    @Get("/users/{login}")
    fun get(@PathVariable login: String): UserEntity? {
        return userService.findByLogin(login)
    }

}
