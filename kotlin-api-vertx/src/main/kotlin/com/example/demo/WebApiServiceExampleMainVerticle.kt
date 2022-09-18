package com.example.demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonObject
//import io.vertx.examples.webapiservice.persistence.TransactionPersistence
//import io.vertx.examples.webapiservice.services.TransactionsManagerService
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
//import io.vertx.ext.web.openapi.RouterBuilder
//import io.vertx.serviceproxy.ServiceBinder
import org.slf4j.LoggerFactory
//import java.lang.Throwable
import kotlin.Array
import kotlin.String
//
//import io.vertx.examples.webapiservice.persistence.TransactionPersistence
//import io.vertx.examples.webapiservice.services.TransactionsManagerService
//import io.vertx.ext.web.openapi.RouterBuilder
//import io.vertx.serviceproxy.ServiceBinder


class WebApiServiceExampleMainVerticle : AbstractVerticle() {
    var server: HttpServer? = null
    var serviceBinder = null
    var consumer: MessageConsumer<JsonObject>? = null
    var LOG = LoggerFactory.getLogger(this.javaClass)

    /**
     * Start transaction service
     */
    /*private fun startTransactionService() {
        serviceBinder = ServiceBinder(vertx)
        val persistence: TransactionPersistence = TransactionPersistence.create()

        // Create an instance of TransactionManagerService and mount to event bus
        val transactionsManagerService: TransactionsManagerService = TransactionsManagerService.create(persistence)
        consumer = serviceBinder
            .setAddress("transactions_manager.myapp")
            .register(TransactionsManagerService::class.java, transactionsManagerService)
    }*/

    /**
     * This method constructs the router factory, mounts services and handlers and starts the http server with built router
     * @return
     */
    private fun startHttpServer(): Future<Void>? {
        /*return RouterBuilder.create(vertx, "openapi.json")
            .onFailure(Throwable::printStackTrace) // In case the contract loading failed print the stacktrace
            .compose { routerBuilder ->
                // Mount services on event bus based on extensions
                routerBuilder.mountServicesFromExtensions()

                // Generate the router
                val router: Router = routerBuilder.createRouter()
                router.errorHandler(
                    400
                ) { ctx: RoutingContext ->
                    LOG.debug(
                        "Bad Request",
                        ctx.failure()
                    )
                }
                server =
                    vertx.createHttpServer(HttpServerOptions().setPort(8080).setHost("localhost"))
                        .requestHandler(router)
                server.listen().mapEmpty()
            }*/
        return null
    }

    override fun start(promise: Promise<Void>) {
        //startTransactionService()
        startHttpServer()?.onComplete(promise)
    }

    /**
     * This method closes the http server and unregister all services loaded to Event Bus
     */
    override fun stop() {
        server!!.close()
        consumer!!.unregister()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val vertx = Vertx.vertx()
            vertx.deployVerticle(WebApiServiceExampleMainVerticle())
        }
    }
}