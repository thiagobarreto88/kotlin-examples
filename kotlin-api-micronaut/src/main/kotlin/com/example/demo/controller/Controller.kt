package com.example.demo.controller

import com.example.demo.entity.UserEntity
import com.example.demo.exception.UserNotFoundException
import com.example.demo.model.CreateUserRequest
import com.example.demo.model.ErrorMessageModel
import com.example.demo.repository.UserRepository
import io.micronaut.data.exceptions.EmptyResultException
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.annotation.Controller
import jakarta.inject.Inject
import jakarta.inject.Singleton
//import com.example.demo.repository.UserRepository

import java.text.MessageFormat
import java.util.*

@Singleton
@Controller
class Controller (@Inject val userRepository : UserRepository) {

    //@Inject
    //var userRepository : UserRepository

    @Post("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun create(@Body request : CreateUserRequest): String {
        userRepository.save(UserEntity(login = request.login, firstname = request.firstname, lastname = request.lastname, description = request.description))
        return "OK"
    }

    @Get("/users/{login}")
    fun get(@PathVariable login: String): UserEntity? {
        try {
            return userRepository.findByLogin(login) ?: throw UserNotFoundException(login);
        }catch (ex: EmptyResultException){
            throw UserNotFoundException(login)
        }
    }

    /*@ExceptionHandler(value = [(Exception::class)])
    fun handleException(ex: Exception): ResponseEntity<ErrorMessageModel> {
        ex.printStackTrace()

        if (ex is UserNotFoundException){
            var errorMessage = "Usuário {0} não encontrado"
            errorMessage = MessageFormat.format(errorMessage, ex.login)

            println("Usuário não encontrado")
            val errorMessageModel = ErrorMessageModel(
                HttpStatus.NOT_FOUND.value(),
                errorMessage
            )
            return ResponseEntity(errorMessageModel, HttpStatus.NOT_FOUND)
        }else{
            val errorMessage = ErrorMessageModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.message
            )
            return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }*/
}
