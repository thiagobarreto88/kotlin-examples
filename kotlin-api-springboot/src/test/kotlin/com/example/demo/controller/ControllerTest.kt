package com.example.demo.controller

import com.example.demo.exception.UserNotFoundException
import com.example.demo.model.ErrorMessageModel
import com.example.demo.repository.ArticleRepository
import com.example.demo.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.text.MessageFormat

class ControllerTest {

    @Spy val controller = Controller(Mockito.mock(UserRepository::class.java))

    @Test
    fun `test handleUserNotFoundException`() {

        var userNotFoundException = UserNotFoundException("userLogin")
        var errorMessageEntity = controller.handleException(userNotFoundException)

        var expectedErrorMessage = "Usuário {0} não encontrado"
        expectedErrorMessage = MessageFormat.format(expectedErrorMessage, userNotFoundException.login)

        assertNotNull(errorMessageEntity)
        assertEquals(HttpStatus.NOT_FOUND, errorMessageEntity.statusCode)
        assertEquals(404, errorMessageEntity.body?.value)
        assertEquals(expectedErrorMessage, errorMessageEntity.body?.message)

    }

    @Test
    fun `test handleException`() {

        var errorMessageEntity : ResponseEntity<ErrorMessageModel>
        errorMessageEntity = controller.handleException(Exception("Erro genérico"))

        assertNotNull(errorMessageEntity)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorMessageEntity.statusCode)
        assertEquals(500, errorMessageEntity.body?.value)
        assertEquals("Erro genérico", errorMessageEntity.body?.message)

    }

}