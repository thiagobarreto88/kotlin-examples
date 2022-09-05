package com.example.demo.controller

import com.example.demo.entity.Customer
import com.example.demo.exception.UserNotFoundException
import com.example.demo.model.ErrorMessageModel
import com.example.demo.repository.CustomerRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testng.annotations.BeforeClass
import java.text.MessageFormat

//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
@WebMvcTest(Controller::class)
class ControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userRepository : CustomerRepository

    @Spy val controller = Controller(Mockito.mock(CustomerRepository::class.java))

    @Test
    fun `test getReturnUser`() {
        Mockito.`when`(userRepository.findByLogin(Mockito.anyString())).thenReturn(Customer("318756", firstname = "Thiago", lastname = "Barreto", description = null, id = 2))

        mockMvc.get("/users/318756/") {
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("""{"login":"318756","firstname":"Thiago", "lastname": "Barreto", "description": null, "id": 2}""") }
        }

    }
    @Test
    fun `test getReturnNotFound`() {

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