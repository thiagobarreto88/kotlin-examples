package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.exception.UserNotFoundException
import com.example.demo.model.CreateUserRequest
import com.example.demo.model.ErrorMessageModel
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.MessageFormat
import java.util.*

@RestController
class Controller (@Autowired private val userRepository: UserRepository){

    @PostMapping("/")
    fun create(@RequestBody request : CreateUserRequest): String {
        userRepository.save(User(login = request.login, firstname = request.firstname, lastname = request.lastname, description = request.description))
        return "OK"
    }

    @GetMapping("/users/{login}")
    fun get(@PathVariable login: String): User? {

        return userRepository.findByLogin(login) ?: throw UserNotFoundException(login);

    }

    @ExceptionHandler(value = [(Exception::class)])
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

    }
}
