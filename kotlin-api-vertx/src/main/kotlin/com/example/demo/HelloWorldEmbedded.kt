package com.example.demo

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerRequest

class HelloWorldEmbedded {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Create an HTTP server which simply returns "Hello World!" to each request.
            Vertx.vertx().createHttpServer().requestHandler { req: HttpServerRequest ->
                req.response().end("Hello World!")
            }.listen(8080)
        }
    }
}