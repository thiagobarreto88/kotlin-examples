package com.example.demo.exception.handler

import com.example.demo.dto.ErrorMessageDTO
import com.example.demo.exception.UserNotFoundException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import java.text.MessageFormat


@Produces
@Singleton
class UserNotFoundExceptionHandler: ExceptionHandler<UserNotFoundException, HttpResponse<ErrorMessageDTO>> {

    override fun handle(request: HttpRequest<*>?, exception: UserNotFoundException): HttpResponse<ErrorMessageDTO> {
        return HttpResponse.notFound(ErrorMessageDTO(url = request?.path, message = MessageFormat.format("Usuário {0} não encontrado", exception.login)))
    }
}