package com.example.demo.controller

import io.vertx.core.AbstractVerticle
import io.vertx.core.Launcher
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.Router.router
import io.vertx.ext.web.RoutingContext


class RoutesController: AbstractVerticle() {

    override fun start() {

        val vertx = Vertx.vertx()
        val server: HttpServer = vertx.createHttpServer()
        val router = router(vertx)
        val route = router.route(HttpMethod.GET, "/users")

        route.handler { ctx: RoutingContext ->
            val response = ctx.response()
            // enable chunked responses because we will be adding data as
            // we execute over other handlers. This is only required once and
            // only if several handlers do output.
            response.isChunked = true
            response.write("route1\n")
            response.end("Hello World from Vert.x-Web!");

        }
        server.requestHandler(router).listen(8080);

    }
}