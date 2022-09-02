package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller (val userRepository: UserRepository){
    @PostMapping("/")
    fun create(): String {
        userRepository.save(User(login = "abc", firstname = "Thiago", lastname = "Barreto"))
        return "OK"
    }

    @GetMapping("/users/{login}")
    fun get(@PathVariable login: String): User? {

        return userRepository.findByLogin(login) ?: throw UserNotFoundException();

    }

    @ExceptionHandler
    fun handleUserNotFoundException(ex: UserNotFoundException): ResponseEntity<ErrorMessageModel> {

        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            "Usuário não encontrado"
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleUserNotFoundException(ex: Exception): ResponseEntity<ErrorMessageModel> {

        val errorMessage = ErrorMessageModel(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorMessageModel(val value: Int, val message: String?)  {

}

class UserNotFoundException : Throwable() {

}
