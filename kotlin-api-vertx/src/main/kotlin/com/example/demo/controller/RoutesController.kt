package com.example.demo.controller

import com.example.demo.dto.ErrorMessageDTO
import com.example.demo.entity.UserEntity
import com.example.demo.exception.UserNotFoundException
import com.example.demo.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.handler.codec.http.HttpHeaderValues
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpHeaders
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Router.router
import io.vertx.ext.web.RoutingContext
import java.text.MessageFormat
import java.util.*


class RoutesController: AbstractVerticle() {

    //val vertx = Vertx.vertx()

    private val userService = UserService()

    val objectMapper = ObjectMapper()

    override fun start() {

        //val vertx = Vertx.vertx()
        val server: HttpServer = vertx.createHttpServer()
        val router = router(vertx)
        val route = router.route(HttpMethod.GET, "/users/:user_id")

        val usersMock : List<UserEntity> = listOf(UserEntity(firstname = "Thiago", lastname = "Barreto", login = "123"))

        route.handler { context: RoutingContext ->

            val userId : String? = context.pathParam("user_id")

            val response = context.response()
            response.isChunked = true

            if(userId?.isNotBlank() == true){

                try{
                    handleUserFound(userId, response)

                }catch (userNotFoundException: UserNotFoundException){
                    userNotFoundException.printStackTrace()
                    handleUserNotFound(response, userId)
                }catch (ex: Exception){
                    ex.printStackTrace()
                    handleInternalError(response)
                }

            }else{
                handleUserNotFound(response, userId)

            }

        }
        server.requestHandler(router).listen(8080);

    }

    private fun handleUserFound(
        userId: String,
        response: HttpServerResponse
    ) {
        var userFromRequest = userService.findByLogin(userId)

        response.statusCode = HttpResponseStatus.OK.code()
        response.statusMessage = "OK"
        response.putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)

        val responseJson = objectMapper.writeValueAsString(userFromRequest)
        response.write(responseJson)
        response.end();
    }

    private fun handleInternalError(response: HttpServerResponse) {
        val message = "Internal error"

        response.statusCode = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()
        response.statusMessage = message
        response.putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)

        response.write(message)
        response.end();
    }

    private fun handleUserNotFound(response: HttpServerResponse, userId: String?) {
        response.statusCode = HttpResponseStatus.NOT_FOUND.code()
        response.statusMessage = "User not found"
        response.putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)

        val errorMessageDTO : ErrorMessageDTO = ErrorMessageDTO(message = MessageFormat.format("User {0} not found", userId))
        response.write(objectMapper.writeValueAsString(errorMessageDTO))
        response.end()
    }

}