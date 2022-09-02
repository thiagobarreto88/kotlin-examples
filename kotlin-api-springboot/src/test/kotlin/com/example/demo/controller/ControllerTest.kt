package com.example.demo.controller

import com.example.demo.exception.UserNotFoundException
import com.example.demo.model.ErrorMessageModel
import com.example.demo.repository.ArticleRepository
import com.example.demo.repository.UserRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.text.MessageFormat

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class ControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    //@MockBean
    //private var repository: UserRepository

    @Spy val controller = Controller(Mockito.mock(UserRepository::class.java))

    //@Spy val controller = Controller(@UserRepository)

    @Test
    fun `test get`() {

        mockMvc.get("/users/userLogin/") {
        }.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("""{"value":404,"message":"Usuário userLogin não encontrado"}""") }
        }

    }

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